locals {
  is_windows = length(regexall("^[a-z]:", lower(abspath(path.root)))) > 0
}

output "absolute_path" {
    value = abspath(path.root)
}

output "operating_system" {
    value = local.is_windows ? "Windows" : "Linux"
}
