## 开发命令

### get

```shell
go env -w GOPROXY=https://goproxy.cn,direct
# go env -w GOPROXY=https://proxy.golang.org,direct
# go env -w GOPROXY=https://goproxy.io,direct
# go env -w GOPROXY=https://mirrors.aliyun.com/goproxy,direct
# go env -w GOPROXY=https://mirrors.cloud.tencent.com/go,direct
go get -u github.com/urfave/cli/v2
go get -u github.com/xuxiaowei-com-cn/git-go@main
go get -u gopkg.in/yaml.v3
```

### mod

```shell
go mod tidy
```

```shell
go mod download
```

### run

```shell
go run main.go
```

### run help

- Windows 环境为 %xxx%
- Linux 环境为 $xxx

```shell
go run main.go --help
```

```shell
$ go run main.go --help
NAME:
   gitbot-go - 基于 Go 语言开发的 GitLab 命令行工具

USAGE:
   gitbot-go [global options] command [command options]

VERSION:
   dev

AUTHOR:
   徐晓伟 <xuxiaowei@xuxiaowei.com.cn>

COMMANDS:
   help, h  Shows a list of commands or help for one command

GLOBAL OPTIONS:
   --help, -h     show help
   --version, -v  print the version

COPYRIGHT:
   徐晓伟工作室 <xuxiaowei@xuxiaowei.com.cn>
```
