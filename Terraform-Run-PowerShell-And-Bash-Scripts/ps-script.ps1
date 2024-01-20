$json = [Console]::In.ReadLine() | ConvertFrom-Json

$foobaz = @{foobaz = "$($json.foo) $($json.baz)"}
Write-Output $foobaz | ConvertTo-Json