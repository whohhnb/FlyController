# FlyController

### 关于

 通过命令来管理玩家飞行（可调整单人、全部人），并且可以自定义修改配置文件的信息（支持RGB），可以提供玩家使用某指令的权限，可以管理对某世界的飞行控制

### 兼容性
 
 插件支持Minecraft 1.20+的版本，且支持Folia
 
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

### 配置文件

```
# 允许的世界
enabled-worlds:
  - world
  - world_nether
  - world_the_end

# 在退出时保持飞行状态
keep-fly-on-logout: false

# 在进入时保持飞行
join-fly: true

# 输出信息（支持RGB）
messages:
  no-permission: "&#FF5555✘&#FF6666 你&#FF7777没&#FF8888有&#FF9999权&#FFAAAA限"
  player-only: "&#FFAA00⚠&#FFBB11 该&#FFCC22命&#FFDD33令&#FFEE44需&#FFFF55要&#FFEE66玩家"
  player-not-found: "&#FF5555玩&#FF6666家 &#FF7777%player% &#FF8888不&#FF9999存&#FFAAAA在"
  fly-enabled: "&#55FF55✓&#66FF66 飞&#77FF77行&#88FF88已&#99FF99启&#AAFFAA动"
  fly-disabled: "&#FF5555✕&#FF6666 飞&#FF7777行&#FF8888已&#FF9999禁&#FFAAAA用"
  fly-enabled-other: "&#55FF55➜&#66FF66 已&#77FF77为 %player% &#88FF88启&#99FF99动"
  fly-disabled-other: "&#FF5555➜&#FF6666 已&#FF7777为 %player% &#FF8888禁&#FF9999用"
  reload-success: "&#FFAA00⟳&#FFBB11 配&#FFCC22置&#FFDD33已&#FFEE44重&#FFFF55载"
  all-enabled: "&#55FF55🌍&#66FF66 全&#77FF77体&#88FF88飞&#99FF99行&#AAFFAA启用"
  all-disabled: "&#FF5555🌐&#FF6666 全&#FF7777体&#FF8888飞&#FF9999行&#FFAAAA禁用"
```
