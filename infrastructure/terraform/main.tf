provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "panda" {
  count                     = 2
  ami                       = "ami-07ebfd5b3428b6f4d"
  instance_type             = "t2.micro"
  availability_zone         = var.ec2_availability_zone
  key_name                  = var.aws_key_name
  vpc_security_group_ids    = ["${var.security_group}"]

  connection {
    host        = self.public_ip
    type        = "ssh"
    user        = "ubuntu"
    private_key = file("~/.ssh/id_rsa")
  }
}

resource "aws_elb" "panda" {
  name               = "panda-load-balancer"
  availability_zones = var.elb_availability_zones
  security_groups   = ["${var.security_group}"]

  health_check {
    healthy_threshold   = 2
    unhealthy_threshold = 2
    timeout             = 3
    interval            = 30
    target              = "HTTP:8080/"
  }

  listener {
    lb_port           = 80
    lb_protocol       = "http"
    instance_port     = "8080"
    instance_protocol = "http"
  }

  instances = aws_instance.panda.*.id
}

resource "local_file" "ansible_inventory" {
    content     = templatefile("inventory.tpl", { ansible_ip =  "${join("\n", aws_instance.panda.*.public_ip)}" })
    filename = "${path.module}/../ansible/inventory"
}

output "elb_dns_name" {
  value = aws_elb.panda.dns_name
}

