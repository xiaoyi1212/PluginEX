# PluginEX

适用于SpigotMC的插件管理器

<hr>

## 语言

* [English](README.md)
* [简体中文](README-zh-CN.md)

## 适用于

> Minecraft 1.8.X - 1.20.4

* SpigotMC
* CraftBukkit
* PaperMC
* CatServer

## 命令

`/pluginex` 或 `/pex`

### load

<p>加载一个插件到服务器</p>

* name - 插件文件名.

> /pluginex load \<name>

### disable

<p>禁用一个插件</p>

* name - 插件名.

> /pluginex disable \<name>
> 
### list

<p>列出所有已加载的插件.</p>

> /pluginex list

### reload

<p>重载指定插件.</p>

* name - Plugin file name.

> /pluginex reload \<name>

## 权限

* `pluginex.admin.*` 所有管理员权限
* `pluginex.admin.command` 命令使用权限
* `pluginex.admin.update` 插件更新权限
* `pluginex.admin.gui` GUI显示权限
