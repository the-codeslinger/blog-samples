Function ConvertTo-Base64 
{
    [CmdletBinding()]
    param (
        [Parameter(ValueFromPipeline = $true)]
        [array] $Data
    )
    
    Process 
    {
        if ($null -ne $Data) 
        {
            $Bytes = [System.Text.Encoding]::UTF8.GetBytes($Data)
            return [Convert]::ToBase64String($Bytes)
        }
        else 
        {
            Write-Error "No input data provided."
            return
        }
    }

    <#
    .SYNOPSIS

    Converts data to base64-encoded format.

    .DESCRIPTION

    The Convert-ToBase64 function converts data to base64-encoded format. 
    The function accepts an array of data as input and returns the base64-encoded string.
    Pipeline input is supported.

    .PARAMETER Data
    The data to be converted to base64-encoded format.
    Can be a string, byte array, or any other data type that can be converted to a byte array.

    .OUTPUTS

    System.String

    .EXAMPLE

    PS> Convert-ToBase64 "Why can't dinosaurs clap their hands? Because they're extinct."
    V2h5IGNhbid0IGRpbm9zYXVycyBjbGFwIHRoZWlyIGhhbmRzPyBCZWNhdXNlIHRoZXkncmUgZXh0aW5jdC4=

    .EXAMPLE

    PS> Convert-ToBase64 -Data "I'm afraid for the calendar. Its days are numbered."     
    SSdtIGFmcmFpZCBmb3IgdGhlIGNhbGVuZGFyLiBJdHMgZGF5cyBhcmUgbnVtYmVyZWQu

    .EXAMPLE

    PS> "I was going to try an all almond diet, but that's just nuts." | Convert-ToBase64
    SSB3YXMgZ29pbmcgdG8gdHJ5IGFuIGFsbCBhbG1vbmQgZGlldCwgYnV0IHRoYXQncyBqdXN0IG51dHMu

    .LINK
    https://the-codeslinger.com
    #>
}

Function ConvertFrom-Base64
{
    [CmdletBinding()]
    param (
        [Parameter(ValueFromPipeline = $true)]
        [string] $Base64
    )
    
    Process 
    {
        if ($null -ne $Base64) 
        {
            $Bytes = [Convert]::FromBase64String($Base64)
            return [System.Text.Encoding]::UTF8.GetString($Bytes)
        }
        else 
        {
            Write-Error "No base64-encoded data provided."
            return
        }
    }

    <#
    .SYNOPSIS

    Converts base64-encoded data to its original form.

    .DESCRIPTION

    The Convert-FromBase64 function converts base64-encoded data to its original form.
    The function accepts a base64-encoded string as input and returns the original data as a string.
    Pipeline input is supported.

    .PARAMETER Base64

    The base64-encoded data to be converted to its original form.

    .OUTPUTS

    System.String

    .EXAMPLE

    PS> Convert-FromBase64 "V2hhdCBkbyB5b3UgY2FsbCBhIGZhY3RvcnkgdGhhdCBtYWtlcyBva2F5IHByb2R1Y3RzPyBBIHNhdGlzZmFjdG9yeS4="
    What do you call a factory that makes okay products? A satisfactory.

    .EXAMPLE

    PS> Convert-FromBase64 -Base64 "SSBvbmx5IGtub3cgMjUgbGV0dGVycyBvZiB0aGUgYWxwaGFiZXQuIEkgZG9uJ3Qga25vdyB5Lg=="
    I only know 25 letters of the alphabet. I don't know y.

    .EXAMPLE

    "SSBtYWRlIGEgcGVuY2lsIHdpdGggdHdvIGVyYXNlcnMuIEl0IHdhcyBwb2ludGxlc3Mu" | Convert-FromBase64              
    I made a pencil with two erasers. It was pointless.

    .LINK
    https://the-codeslinger.com
    #>
}

Export-ModuleMember -Function ConvertTo-Base64, ConvertFrom-Base64