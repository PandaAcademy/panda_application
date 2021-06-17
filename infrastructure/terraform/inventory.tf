resource "local_file" "ansible_inventory" {
  content = templatefile("inventory.tpl", 
                        { ansible_ip = "${join("\n", aws_instance.panda.*.public_ip)}" })
  filename = "${path.module}/../ansible/inventory"
}