How To Execute PowerShell And Bash Scripts In Terraform
=======================================================

This is a very brief sample on how to execute Linux Bash and Windows PowerShell 
scripts in Terraform.

```shell
terraform init
terraform plan
```

Here is a sample output on Windows.

```shell
PS C:\Apps\Terraform-Run-PowerShell-And-Bash-Scripts> terraform plan
data.external.script: Reading...
data.external.script: Read complete after 1s [id=-]

Changes to Outputs:
  + absolute_path    = "C:/Apps/Terraform-Run-PowerShell-And-Bash-Scripts"
  + foobaz           = "The Codeslinger"
  + operating_system = "Windows"

You can apply this plan to save these new output values to the Terraform state,
without changing any real infrastructure.
```

Here is a sample output on Linux.

```shell
rlo@fedora:~/Documents/Terraform-Detect-Operating-System$ terraform plan
data.external.script: Reading...
data.external.script: Read complete after 1s [id=-]

Changes to Outputs:
  + absolute_path    = "/home/rlo/Documents/Terraform-Run-PowerShell-And-Bash-Scripts"
  + foobaz           = "The Codeslinger"
  + operating_system = "Linux"

You can apply this plan to save these new output values to the Terraform state,
without changing any real infrastructure.
```

You must also set the PowerShell execution policy as admin for scripts. This is 
good enough for testing and your own use. If you regularly execute scripts that 
are not your own, you should choose a narrower permission level or consider 
signing your scripts.

```shell
Set-ExecutionPolicy -ExecutionPolicy Unrestricted -Scope LocalMachine
```
