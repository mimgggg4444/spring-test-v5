package com.daelim.springtest
import kotlinx.coroutines.*

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CoroutineTests {

    @Test
    fun testLaunch() = runBlocking {
        /*
            launch는 새로운 코루틴을 시작하고,
            해당 코루틴에서 비동기 작업을 수행합니다.
            반환값은 Job이며, 이는 작업의 완료를 기다리거나,
            작업을 취소할 때 사용할 수 있습니다.
        */
        val job = launch {
            delay(5000L)
            println("World!")

        }
        print("Hello, ")
        job.join() // 'launch'로 시작된 코루틴이 완료될 때까지 기다립니다.
    }


    @Test
    fun testAsyncAwait() = runBlocking {
        /*
            async는 코루틴을 시작하고,
            Deferred<T>를 반환합니다.
            await는 Deferred 객체로부터 결과를 가져올 때 사용됩니다.
         */
        val deferred = async {
            delay(1000L)
            "Returned Value"
        }
        println("Waiting for value...")
        println(deferred.await()) // 'async'로 시작된 코루틴이 반환한 값을 출력합니다.
    }

    suspend fun performRequest(): String { //suspend fun 을 사용해야 delay 를 함수내에서 사용할 수 있다.
        delay(1000) // 비동기적으로 1초 대기를 시뮬레이션
        return "Response"
    }


    @Test
    fun testSuspendFunction() = runBlocking {
        val result = performRequest() // suspend 함수 호출
        println(result)
    }

    suspend fun fetchDataFromDatabase(): String {
        delay(2000) // 데이터베이스 조회를 위해 2초 대기
        return "Data from database"
    }

    suspend fun fetchNetworkData(): String {
        delay(3000) // 네트워크 요청을 위해 3초 대기
        return "Data from network"
    }

    @Test
    fun testDispatch() {
        runBlocking {
            launch(Dispatchers.IO) { // I/O 작업을 위한 컨텍스트
                val data = fetchDataFromDatabase()
                println(data)
            }
            launch(Dispatchers.Default) { // CPU 집약적 작업을 위한 컨텍스트
                val data = fetchNetworkData()
                println(data)
            }
        }
    }

}