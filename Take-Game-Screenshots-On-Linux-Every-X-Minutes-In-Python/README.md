Take Game Screenshots On Linux Every X Minutes In Python
========================================================

This small Python script interfaces with the DBus daemon on Linux to take screenshots at regular intervals using the [Freedesktop Desktop Portal](https://flatpak.github.io/xdg-desktop-portal/docs/api-reference.html).

The script does not expect any command line arguments.
Instead, the two relevant values for manipulation are defined at the top of the script.

```python
INTERVAL_SECONDS = 60 * 5
SCREENSHOT_LOCATION = "/home/rlo/Pictures/Screenshots"
```

Execute the script the following way.

```shell
python3 dbus-screenshot.py
```

Depending on your desktop, you may be prompted once for permission to take screenshots.