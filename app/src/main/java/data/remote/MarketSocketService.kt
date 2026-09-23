package data.remote

import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketSocketService @Inject constructor(
    private val socket: Socket
) {

    fun connect() {
        if (!socket.connected()) {
            socket.connect()
        }
    }

    fun disconnect() {
        if (socket.connected()) {
            socket.disconnect()
        }
    }

    fun observeConnectionStatus(): Flow<Boolean> = callbackFlow {
        val onConnect = Emitter.Listener {
            trySend(true)
        }
        val onDisconnect = Emitter.Listener { _ ->
            trySend(false)
        }
        val onConnectError = Emitter.Listener { _ ->
            trySend(false)
        }
        val onError = Emitter.Listener { _ ->
        }

        socket.on(Socket.EVENT_CONNECT, onConnect)
        socket.on(Socket.EVENT_DISCONNECT, onDisconnect)
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.on(Socket.EVENT_ERROR, onError)

        if (socket.connected()) {
            trySend(true)
        }

        awaitClose {
            socket.off(Socket.EVENT_CONNECT, onConnect)
            socket.off(Socket.EVENT_DISCONNECT, onDisconnect)
            socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
            socket.off(Socket.EVENT_ERROR, onError)
        }
    }

    fun observeMarketData(): Flow<JSONObject> = callbackFlow {
        val onMarketUpdate = Emitter.Listener { args ->
            val rawData = args.getOrNull(0)

            when (rawData) {
                is JSONObject -> {
                    val resultArray = rawData.optJSONArray("result")
                    if (resultArray != null) {
                        for (i in 0 until resultArray.length()) {
                            val item = resultArray.optJSONObject(i)
                            if (item != null) {
                                trySend(item)
                            }
                        }
                    } else {
                        trySend(rawData)
                    }
                }
                is JSONArray -> {
                    for (i in 0 until rawData.length()) {
                        val item = rawData.optJSONObject(i)
                        if (item != null) {
                            trySend(item)
                        }
                    }
                }
                is String -> {
                    try {
                        val jsonObj = JSONObject(rawData)
                        val resultArray = jsonObj.optJSONArray("result")
                        if (resultArray != null) {
                            for (i in 0 until resultArray.length()) {
                                val item = resultArray.optJSONObject(i)
                                if (item != null) trySend(item)
                            }
                        } else {
                            trySend(jsonObj)
                        }
                    } catch (_: Exception) {
                        try {
                            val jsonArr = JSONArray(rawData)
                            for (i in 0 until jsonArr.length()) {
                                val item = jsonArr.optJSONObject(i)
                                if (item != null) trySend(item)
                            }
                        } catch (_: Exception) {
                        }
                    }
                }
                else -> {
                }
            }
        }

        val events = arrayOf(
            "price_change", "price_update", "market_data", "market", 
            "prices", "update", "message", "data", "symbol", "quote"
        )
        for (event in events) {
            socket.on(event, onMarketUpdate)
        }

        subscribeToMarketData()

        awaitClose {
            for (event in events) {
                socket.off(event, onMarketUpdate)
            }
        }
    }.flowOn(Dispatchers.IO)

    fun subscribeToMarketData() {
        if (socket.connected()) {
            socket.emit("subscribe", "market_prices")
            socket.emit("subscribe")
            socket.emit("subscribe", "market")
            socket.emit("join", "market")
            socket.emit("sub", "market")
        }
    }
}
