# provider "aws" {
#   region = "us-east-1"
#   profile = "panda" # tylko gdy istnieje taki --profile w ~/.aws/credentials
# }

# resource "aws_vpc" "vpc" {
#     cidr_block = "10.83.0.0/16"
#     enable_dns_support   = true
#     enable_dns_hostnames = true
#     tags       = {
#         Name = "Terraform VPC"
#     }
# }

# resource "aws_internet_gateway" "internet_gateway" {
#     vpc_id = aws_vpc.vpc.id
# }


# resource "aws_route_table" "public_route_table" {
#     vpc_id = aws_vpc.vpc.id

#     route {
#         cidr_block = "0.0.0.0/0"
#         gateway_id = aws_internet_gateway.internet_gateway.id
#     }
# }

# resource "aws_route_table_association" "public_route_table_association" {
#     count = length(aws_subnet.pub_subnet)
#     subnet_id = aws_subnet.pub_subnet[count.index].id
#     route_table_id = aws_route_table.public_route_table.id
# }

# resource "aws_subnet" "pub_subnet" {
#     vpc_id                  = aws_vpc.vpc.id
#     count = length(var.availability_zones)
#     cidr_block        = cidrsubnet(aws_vpc.vpc.cidr_block, 8, count.index)
#     map_public_ip_on_launch = true
#     availability_zone = var.availability_zones[count.index]
# }

# resource "aws_instance" "panda" {
#   count                     = length(var.availability_zones)
#   ami                       = "ami-0885b1f6bd170450c"
#   instance_type             = "t2.micro"
#   availability_zone         = var.availability_zones[count.index] 
#   key_name                  = var.aws_key_name
#   vpc_security_group_ids    = [aws_security_group.sg-pub.id]
#   subnet_id = aws_subnet.pub_subnet[count.index].id

#   connection {
#     host        = self.public_ip
#     type        = "ssh"
#     user        = "ubuntu"
#     private_key = file(var.ssh_key_path)
#   }

#   provisioner "remote-exec" {
#     inline = [
#       "echo \"Hello, World ${self.public_ip}\" > index.html",
#       "nohup busybox httpd -f -p 8080 &",
#       "sleep 1",
#     ]
#   }
# }

# resource "aws_security_group" "sg-pub" {
#     vpc_id      = aws_vpc.vpc.id

#     ingress {
#         from_port       = 80
#         to_port         = 80
#         protocol        = "tcp"
#         cidr_blocks     = ["0.0.0.0/0"]
#     }

#     ingress {
#         from_port       = 8080
#         to_port         = 8080
#         protocol        = "tcp"
#         cidr_blocks     = ["0.0.0.0/0"]
#     }

#     ingress {
#         from_port       = 22
#         to_port         = 22
#         protocol        = "tcp"
#         cidr_blocks     = ["0.0.0.0/0"]
#     }

#     egress {
#         from_port       = 0
#         to_port         = 65535
#         protocol        = "tcp"
#         cidr_blocks     = ["0.0.0.0/0"]
#     }
# }

# resource "aws_lb" "alb" {
#     name               = "alb"
#     internal           = false
#     load_balancer_type = "application"
#     security_groups    =  [aws_security_group.sg-pub.id]
#     subnets            =  aws_subnet.pub_subnet[*].id
# }

# resource "aws_lb_listener" "alb-listener" {
#   load_balancer_arn = aws_lb.alb.arn
#   port              = "80"
#   protocol          = "HTTP"

#   default_action {
#     type             = "forward"
#     target_group_arn = aws_lb_target_group.tg.arn
#   }
# }

# resource "aws_lb_target_group" "tg" {
#   name     = "tg"
#   port     = 8080
#   protocol = "HTTP"
#   target_type = "instance"
#   vpc_id   = aws_vpc.vpc.id
# }

# resource "aws_lb_target_group_attachment" "tg-attch" {
#   count = length(aws_instance.panda)
#   target_group_arn = aws_lb_target_group.tg.arn
#   target_id        = aws_instance.panda[count.index].id
#   port             = 8080
# }

# output "elb_dns_name" {
#   value = aws_lb.alb.dns_name
# }
