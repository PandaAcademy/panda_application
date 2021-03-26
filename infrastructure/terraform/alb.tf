resource "aws_lb" "alb" {
    name               = "alb"
    internal           = false
    load_balancer_type = "application"
    security_groups    =  [aws_security_group.sg-pub.id]
    subnets            =  aws_subnet.pub_subnet[*].id
}

resource "aws_lb_listener" "alb-listener" {
  load_balancer_arn = aws_lb.alb.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.tg.arn
  }
}

resource "aws_lb_target_group" "tg" {
  name     = "tg"
  port     = 8080
  protocol = "HTTP"
  target_type = "instance"
  vpc_id   = aws_vpc.vpc.id
}

resource "aws_lb_target_group_attachment" "tg-attch" {
  count = length(aws_instance.panda)
  target_group_arn = aws_lb_target_group.tg.arn
  target_id        = aws_instance.panda[count.index].id
  port             = 8080
}