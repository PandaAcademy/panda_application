variable "ec2_availability_zone" {
  description = "Zone w ktorym dostepne sa instancje"
  default     = "us-east-1a"
}

variable "elb_availability_zones" {
  description = "Zone'y w ktorym dostepny jest ELB. Jedna z nich musi pokrywac sie z instancja"
  default     = ["us-east-1a", "us-east-1b"]
}

variable "security_group" {
  description = "Security group uzyta dla ELB oraz EC2"
}

variable "aws_key_name" {
  description = "Nawa klucza dodanego w AWS"
}

variable "ssh_key_path" {
  description = "Sciezka do klucza SSH"
}

