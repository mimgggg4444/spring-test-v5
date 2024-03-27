package com.daelim.springtest

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@SpringBootTest
class SpringTestApplicationTests {

	@Test
	fun testThread() {
		val thread = Thread {
			println("스레드 시작: ${Thread.currentThread().name}")
			Thread.sleep(5000) // 5초 동안 스레드를 잠시 멈춤
			println("스레드 종료: ${Thread.currentThread().name}")
		}
		thread.start()
		println("메인 스레드: ${Thread.currentThread().name}")
		thread.join() // 메인 테스트 스레드가 thread의 작업이 끝날 때까지 기다립니다.
	}

	@Test
	fun testCoroutine(): Unit = runBlocking { // 메인 스레드를 블록하는 상위 코루틴
		launch { // 새로운 코루틴을 시작하고, 비동기적으로 작업 수행
			println("코루틴 시작: ${Thread.currentThread().name}")
			delay(5000) // 5초 동안 코루틴을 잠시 멈춤
			println("코루틴 종료: ${Thread.currentThread().name}")
		}
		println("메인 스레드: ${Thread.currentThread().name}")
	}

	@Test
	fun testThread1000() {
		//이 코드는 10,000개의 스레드를 생성하고, 각 스레드에서 1초간 대기한 후 메시지를 출력합니다. 모든 스레드가 종료될 때까지 기다린 후 전체 실행 시간을 출력합니다.
		val startTime = System.currentTimeMillis()
		val threads = List(10000) {
			Thread {
				Thread.sleep(1000)
				println("${Thread.currentThread()} has finished.")
			}
		}

		threads.forEach { it.start() }
		threads.forEach { it.join() }

		val endTime = System.currentTimeMillis()
		println("Total time with Threads: ${endTime - startTime}ms")
	}

	@Test
	fun testCoroutine1000(): Unit = runBlocking {
		//이 코드는 10,000개의 코루틴을 생성하고, 각 코루틴에서 1초간 대기한 후 메시지를 출력합니다. 모든 코루틴이 종료될 때까지 기다린 후 전체 실행 시간을 출력합니다.
		val startTime = System.currentTimeMillis()
		val jobs = List(10000) {
			launch {
				delay(1000)
				println("${Thread.currentThread()} has finished.")
			}
		}

		jobs.forEach { it.join() }

		val endTime = System.currentTimeMillis()
		println("Total time with Coroutines: ${endTime - startTime}ms")
	}

}