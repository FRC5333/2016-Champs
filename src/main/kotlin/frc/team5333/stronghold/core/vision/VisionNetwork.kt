package frc.team5333.stronghold.core.vision

import java.io.InputStream
import java.math.BigInteger
import java.net.ServerSocket
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*

enum class VisionNetwork {
    INSTANCE;

    lateinit var thread: Thread
    lateinit var server: ServerSocket
    var listening = true
    var connected = false

    fun bytesToInt(arr: ByteArray): Int = BigInteger(arr).toInt()
    fun bytesToFloat(arr: ByteArray): Float = java.lang.Float.intBitsToFloat(BigInteger(arr).toInt())

    fun intToBytes(int: Int): ByteArray = ByteBuffer.allocate(4).putInt(int).order(ByteOrder.BIG_ENDIAN).array()
    fun floatToBytes(float: Float): ByteArray = intToBytes(java.lang.Float.floatToIntBits(float))

    fun readInt(input: InputStream): Int {
        var ba = ByteArray(4)
        input.read(ba, 0, 4)
        return bytesToInt(ba)
    }

    fun readFloat(input: InputStream): Float {
        var ba = ByteArray(4)
        input.read(ba, 0, 4)
        return bytesToFloat(ba)
    }

    fun start() {
        thread = Thread({
            server = ServerSocket(5802)
            while(listening) {
                var socket = server.accept()

                Thread({ handleSocket(socket) }).start()
            }
        })
        thread.name = "Vision Network"
        thread.start()
    }

    fun handleSocket(socket: Socket) {
        try {
            connected = true
            while (true) {
                var inp = socket.inputStream

                var negotiation = readInt(inp)
                if (negotiation == 0xBA) {
                    var activeRect = readInt(inp)
                    var rects: ArrayList<VisionRectangle> = ArrayList()
                    while(readInt(inp) == 0xBB) {
                        var rect = VisionRectangle()
                        rect.x = readInt(inp).toDouble()
                        rect.y = readInt(inp).toDouble()
                        rect.width = readInt(inp).toDouble()
                        rect.height = readInt(inp).toDouble()
                        rects.add(rect)
                    }
                }
            }
        } catch (e: Exception) {
            if (!socket.isClosed) socket.close()
        }
        connected = false
    }
}