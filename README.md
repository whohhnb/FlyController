# FlyController
 通过命令来管理玩家飞行（可调整单人、全部人），并且可以自定义修改配置文件的信息（支持RGB），可以提供玩家使用某指令的权限，可以管理对某世界的飞行控制

### 指令补全
![alt text](/img/image.png)

### 帮助
![alt text](/img/image1.png)

### 权限

```
  fly.toggle:
    description: 开启飞行
    default: op
  fly.others:
    description: 切换玩家飞行
    default: op
  fly.bypass:
    description: 跳过世界限制
    default: op
  fly.reload:
    description: 重载配置文件
    default: op
  fly.join:
    description: 在进入服务器时开启飞行
    default: op
  fly.all:
    description: 为所有玩家开启飞行
    default: op
```