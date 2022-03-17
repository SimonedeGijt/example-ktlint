package org.example

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping

@RestController
class PublicController {
        @GetMapping("/greetings")
    fun homepage():List<String> = listOf("hello")
}
