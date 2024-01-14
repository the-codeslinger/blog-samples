How To Detect Windows Or Linux Operating System In Terraform
============================================================

This is a very brief sample on how to differentiate between Windows and Unix-based operating systems in Terraform

```shell
terraform init
terraform plan
```

Here is a sample output on Windows.

```shell
PS C:\Apps\Terraform-Detect-Operating-System> terraform plan

Changes to Outputs:
  + absolute_path    = "C:/Apps/Terraform-Detect-Operating-System"
  + operating_system = "Windows"

You can apply this plan to save these new output values to the Terraform state,
without changing any real infrastructure.
```

Here is a sample output on Linux.

```shell
rlo@fedora:~/Documents/Terraform-Detect-Operating-System$ terraform plan

Changes to Outputs:
  + absolute_path    = "/home/rlo/Documents/Terraform-Detect-Operating-System"
  + operating_system = "Linux"

You can apply this plan to save these new output values to the Terraform state,
without changing any real infrastructure.
```