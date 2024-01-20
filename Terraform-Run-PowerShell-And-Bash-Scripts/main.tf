locals {
  is_windows = length(regexall("^[a-z]:", lower(abspath(path.root)))) > 0
  shell_name = local.is_windows ? "powershell.exe" : "bash"
  script_name = local.is_windows ? "ps-script.ps1" : "bash-script.sh"
}

data "external" "script" {
  program = [
    local.shell_name, "${path.module}/${local.script_name}"
  ]
  query = {
    foo = "The"
    baz = "Codeslinger"
  }
}

output "absolute_path" {
    value = abspath(path.root)
}

output "operating_system" {
    value = local.is_windows ? "Windows" : "Linux"
}

output "foobaz" {
  value = data.external.script.result.foobaz
}