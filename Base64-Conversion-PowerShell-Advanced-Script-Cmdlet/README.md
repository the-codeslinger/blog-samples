Base64 Conversion PowerShell Advanced Script Cmdlet
===================================================

This example shows how to create advanced PowerShell functions that behave like a cmdlet.
The samples implement conversion functions for the Base64 encoding scheme.
Both functions can be used standalone or in a pipeline.

Copy the folder `Convert-Base64` to one of the directories listed by `$env:PSModulePath`.

Restart PowerShell or import the module.

```PowerShell
PS> Import-Module Convert-Base64
```

Call the cmdlets directly or pipe data into them.

```PowerShell
ConvertTo-Base64 -Data "I'm afraid for the calendar. Its days are numbered." | ConvertFrom-Base64
I'm afraid for the calendar. Its days are numbered.
```


[Read the blog post for details.](https://the-codeslinger.com/2024/06/23/base64-powershell-cmdlet-via-advanced-functions/)