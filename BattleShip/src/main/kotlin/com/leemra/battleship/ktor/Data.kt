package com.leemra.battleship.ktor

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.IntArraySerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class Coordinates(
    val x: Int,
    val y: Int,
    val orientation: Int
)

@Serializable
data class FiredList(
    @Serializable(with = FiredDataSerializer::class)
    val list: ArrayList<IntArray>,
    val gameOver: Boolean,
    val playerWinner: Boolean
)

@Serializable
data class PlaceShip(
    @Serializable(with = FiredDataSerializer::class)
    val list: ArrayList<IntArray>
)

class FiredDataSerializer : KSerializer<ArrayList<IntArray>> {
    val serializer = ListSerializer(IntArraySerializer())
    override val descriptor: SerialDescriptor = serializer.descriptor
    override fun deserialize(decoder: Decoder): ArrayList<IntArray> {
        return ArrayList(serializer.deserialize(decoder))
    }

    override fun serialize(
        encoder: Encoder,
        value: ArrayList<IntArray>
    ) {
        serializer.serialize(encoder, value)
    }
}