# Useful Notes  

## Useful Links  

[Original course repo](https://github.com/Clement-Jean/grpc-java-course)  
[gRPC Server Reflection Tutorial](https://github.com/grpc/grpc-java/blob/master/documentation/server-reflection-tutorial.md)  
[Evans GitHub page](https://github.com/ktr0731/evans)  
[Evans installation files](https://github.com/ktr0731/evans/releases)  

## Evans Installation  

```
1-Access this page:
https://github.com/ktr0731/evans/releases
and download the version that matches your OS (in my case evans_linux_amd64.tar.gz worked for me).
2-Unzip the compressed file. It generates a folder like evans_linux_amd64. I moved that folder to my home directory.
3-Inside the folder there is only one binary file that I granted permission: chmod +x evans
4-Use .bashrc to put the folder evans_linux_amd64 in the PATH.
```

## Evans Commands  

```
evans
evans --host localhost --port 50052 --reflection repl (type exit to quit)
show package
show service
show message

To call an endpoint you can use:
call endpoint-name

examples:
call sum
call primes
call avg (ctrl + d to stop entering values)
call max (ctrl + d to stop entering values)
```
