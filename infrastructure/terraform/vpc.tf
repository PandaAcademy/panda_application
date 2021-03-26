resource "aws_vpc" "vpc" {
    cidr_block = "10.83.0.0/16"
    enable_dns_support   = true
    enable_dns_hostnames = true
    tags       = {
        Name = "Terraform VPC"
    }
}

resource "aws_internet_gateway" "internet_gateway" {
    vpc_id = aws_vpc.vpc.id
}


resource "aws_route_table" "public_route_table" {
    vpc_id = aws_vpc.vpc.id

    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.internet_gateway.id
    }

    tags = {
        Name = "rt-pub"
  }
}

resource "aws_route_table_association" "public_route_table_association" {
    count = length(aws_subnet.pub_subnet)
    subnet_id = aws_subnet.pub_subnet[count.index].id
    route_table_id = aws_route_table.public_route_table.id
}

resource "aws_subnet" "pub_subnet" {
    vpc_id                  = aws_vpc.vpc.id
    count = length(var.availability_zones)
    cidr_block        = cidrsubnet(aws_vpc.vpc.cidr_block, 8, count.index)
    map_public_ip_on_launch = true
    availability_zone = var.availability_zones[count.index]
}