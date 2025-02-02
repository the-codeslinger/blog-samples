Take Game Screenshots On Linux Every X Minutes In Python
========================================================

The most viable solution I found was using DBus and calling into the XDG Desktop
Portal to capture the screen. From my understanding, the desktop environment’s
compositor implements this specification and serves the request, e.g., Gnome’s
Mutter.

The solution I present here is based on 
    [this StackOverflow response](https://stackoverflow.com/a/72492093/1651116). 
All of the credit goes to that user. I added a bit of context and explanation in
this blog post. Note that this is not a DBus tutorial, although I implicitly
tackle some core concepts when explaining the code. I would direct you to the 
    [Freedesktop tutorial on DBus](https://dbus.freedesktop.org/doc/dbus-tutorial.html) 
for a high-level overview. I am not a DBus specialist, and some aspects still
elude me.

## Preparations

DBus requires an application loop for its event processing. There are several
integrations available, of which my example uses GLib. This part is explained in
the 
    [Python DBus tutorial](https://dbus.freedesktop.org/doc/dbus-python/tutorial.html#making-asynchronous-method-calls).

```python
dbus.mainloop.glib.DBusGMainLoop(set_as_default=True)
loop = GLib.MainLoop()
```

To get everything running, you start the loop.

```python
self.loop.run()
```

You do not want to do this immediately, however. First, more setup is required
to prepare all the necessary infrastructure. The `run()` function is blocking, so
you want to have everything ready before calling it. The first thing required is
a connection to the user’s session bus. See 
    [this SO answer](https://stackoverflow.com/a/71254213/1651116) 
for a very brief explanation of what it is if you have not read the tutorial.

```python
bus = dbus.SessionBus()
```

The next step is to get a proxy for the remote 
    [Desktop portal](https://flatpak.github.io/xdg-desktop-portal/docs/api-reference.html). 
This proxy allows for invoking several functions, one of which is
`Screenshot()`.

```python
desktop_proxy = bus.get_object("org.freedesktop.portal.Desktop", "/org/freedesktop/portal/desktop")
```

Before invoking the `Screenshot()` function, you require a handler function to
process the responses. For that, you create a unique response path based on the
unique name of your application and a token. See the 
    [Request Interface](https://flatpak.github.io/xdg-desktop-portal/docs/doc-org.freedesktop.portal.Request.html)
documentation for more information.

Here’s how to create the response path. I used a randomly generated UUID,
replacing the hyphens with underscores. Hyphens are not allowed by DBus.

```python
my_name = bus.get_connection().get_unique_name()[1:].replace(".", "_")
response_path = f"/org/freedesktop/portal/desktop/request/{my_name}/{dbus_token}"
```

Now, you can register a signal receiver on the bus.

```python
bus.add_signal_receiver(
    self.response_handler,
    dbus_interface = "org.freedesktop.portal.Request",
    path = response_path,
)
```

There is also a way to 
    [register a handler on the exact proxy](https://dbus.freedesktop.org/doc/dbus-python/dbus.proxies.html#dbus.proxies.Interface.connect_to_signal)
instead of the general bus. I have not figured out the correct parameters,
though.

## Taking The Shot

Now, you can invoke the `Screenshot()` function on the proxy. This method is a
bit of voodoo. I am unsure where the first parameter, “Screenshot,” is defined.
Maybe it is the "second Screenshot" in this 
    [interface description](https://flatpak.github.io/xdg-desktop-portal/docs/doc-org.freedesktop.portal.Screenshot.html#org-freedesktop-portal-screenshot-screenshot)
(`org.freedesktop.portal.Screenshot.Screenshot`)?

The object in the second parameter contains the unique token that is a part of
the response path.

```python
desktop_proxy.Screenshot(
    "Screenshot", 
    {"handle_token": dbus_token}, 
    dbus_interface="org.freedesktop.portal.Screenshot"
)
```

The most simple handler can be as follows. The 
    [documentation](https://flatpak.github.io/xdg-desktop-portal/docs/doc-org.freedesktop.portal.Request.html#org-freedesktop-portal-request-response)
lists three potential values for “response”. The contents of “result” depend on
the type of request. For a screenshot, only “uri” is returned. This is mentioned
in the interface description of Screenshot.


```python
def response_handler(response, result):
    if response == 0:
        print(f'Screenshot file: {result.get("uri")}')
    else:
        print("Failed to get screenshot")
```

## Famous Last Words

I wrapped all this in a `DBusScreenshotLoop` class that executes a screenshot
request every five minutes. I trigger the request with the 
    [GLib.timeout_add_seconds()](https://webreflection.github.io/gjs-documentation/GLib-2.0/GLib.timeout_add_seconds.html)
function. Since my script is also a CLI-only implementation, I added a handler
for CTRL-C that properly stops the main loop.

```python
class DBusScreenshotLoop:
    def __init__(self, screenshot_location):
        self.screenshot_location = screenshot_location
        self.dbus_token = "7fc46846_4d73_416e_95c6_5b4d6ed68492"

        dbus.mainloop.glib.DBusGMainLoop(set_as_default=True)
        self.loop = GLib.MainLoop()

        signal.signal(signal.SIGINT, self.sigint_handler)

    def sigint_handler(self, sig, frame):
        if signal.SIGINT == sig:
            self.loop.quit()
```

It works well enough for me. The only improvements I could make now are the
input parameters for the time interval and the target location.

The Gnome desktop will ask for permission once the script is invoked for the
first time. The screen will also always flash white when a screenshot is taken.
KDE Plasma did not ask for permission and showed no visual feedback.


The script does not expect any command line arguments. Instead, the two relevant
values for manipulation are defined at the top of the script.

```python
INTERVAL_SECONDS = 60 * 5
SCREENSHOT_LOCATION = "/home/rlo/Pictures/Screenshots"
```

Execute the script the following way.

```shell
python3 dbus-screenshot.py
```