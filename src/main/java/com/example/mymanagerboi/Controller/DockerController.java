package com.example.mymanagerboi.Controller;


import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/docker")
public class DockerController {

    @GetMapping("/manage-image")
    public String manageImage(@RequestParam String imageName) throws IOException, InterruptedException {
        return executeDockerCommand("docker ps -a | grep " + imageName).isEmpty() ? startImage(imageName) : restartImage(imageName);
    }

    @GetMapping("/stop-image")
    public String stopImage(@RequestParam String imageName) throws IOException, InterruptedException {
        return stopRunningImage(imageName);
    }

    private String startImage(String imageName) throws IOException, InterruptedException {
        return executeDockerCommand("docker run -d " + imageName);
    }

    private String restartImage(String imageName) throws IOException, InterruptedException {
        return executeDockerCommand("docker restart $(docker ps -a -q  --filter ancestor=" + imageName + ")");
    }

    private String stopRunningImage(String imageName) throws IOException, InterruptedException {
        return executeDockerCommand("docker stop $(docker ps -a -q  --filter ancestor=" + imageName + ")");
    }

    private String executeDockerCommand(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        process.waitFor();
        return reader.lines().collect(Collectors.joining("\n"));
    }
}
