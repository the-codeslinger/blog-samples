import dbus
import dbus.mainloop.glib
import signal
import datetime
import shutil
from gi.repository import GLib
from urllib.parse import unquote, urlparse

# Every 5 minutes.
INTERVAL_SECONDS = 60 * 5
SCREENSHOT_LOCATION = "/home/rlo/Pictures/Screenshots"

class ScreenshotFile:
    """Helper to handle the resulting DBus URI and creating a timestamped path from it.
    Moves the file to the given location using a timestamp as filename.
    """

    def __init__(self, dbus_uri):
        self.dbus_path = unquote(urlparse(str(dbus_uri)).path)

    def store(self, folder):
        now = datetime.datetime.today().strftime("%Y-%m-%d %H.%M.%S")
        new_location = f"{folder}/{now}.png"
        shutil.move(self.dbus_path, new_location)
        return new_location


class DBusScreenshotLoop:
    
    def __init__(self, screenshot_location):
        self.screenshot_location = screenshot_location
        # Randomly generated UUID with the hyphens replaced by underscores.
        # Hyphens are not allowed.
        self.dbus_token = "7fc46846_4d73_416e_95c6_5b4d6ed68492"

        dbus.mainloop.glib.DBusGMainLoop(set_as_default=True)
        self.loop = GLib.MainLoop()

        signal.signal(signal.SIGINT, self.sigint_handler)

    def response_handler(self, response, result):
        """Handles the DBus response. Gets the screenshot file's URI and uses the ScreenshotFile class
        to move it to a target destination. DBus writes the file to "/home/<user>/Pictures".
        """

        if response == 0:
            location = ScreenshotFile(result.get("uri")).store(self.screenshot_location)
            print(f'Screenshot file: {location}')
        else:
            print("Failed to get screenshot")

    def sigint_handler(self, sig, frame):
        """Stop the loop when the user presses CTRL-C in the terminal."""
        
        if signal.SIGINT == sig:
            self.loop.quit()

    def request_screenshot(self, desktop_proxy):
        """Called by GLib.timeout_add_seconds(). Invokes the Dbus Screenshot function."""

        desktop_proxy.Screenshot("Screenshot", {"handle_token": self.dbus_token}, dbus_interface="org.freedesktop.portal.Screenshot")
        return True
    
    def run(self, interval):
        """Set up all the DBus infrastructure, register "self.response_handler()" to handle the result,
        and trigger the "self.request_screenshot()" function in the given interval in seconds.
        """

        # Connect to the DBus user session bus.
        bus = dbus.SessionBus()

        # Get a proxy of the Desktop bus at the given path.
        desktop_proxy = bus.get_object("org.freedesktop.portal.Desktop", "/org/freedesktop/portal/desktop")

        # Set up a handler for receiving signals.
        my_name = bus.get_connection().get_unique_name()[1:].replace(".", "_")
        response_path = f"/org/freedesktop/portal/desktop/request/{my_name}/{self.dbus_token}"

        bus.add_signal_receiver(
            self.response_handler,
            dbus_interface = "org.freedesktop.portal.Request",
            path = response_path,
        )

        GLib.timeout_add_seconds(interval, self.request_screenshot, desktop_proxy)
        self.loop.run()

if __name__ == "__main__":
    screenshot = DBusScreenshotLoop(SCREENSHOT_LOCATION)
    screenshot.run(INTERVAL_SECONDS)
