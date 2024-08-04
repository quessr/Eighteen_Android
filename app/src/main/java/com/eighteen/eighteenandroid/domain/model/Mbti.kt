package com.eighteen.eighteenandroid.domain.model

import com.eighteen.eighteenandroid.common.safeLet

data class Mbti(
    val energy: MbtiType.Energy,
    val information: MbtiType.Information,
    val decision: MbtiType.Decision,
    val lifestyle: MbtiType.Lifestyle
) {
    val mbtiString
        get() = listOf(
            energy,
            information,
            decision,
            lifestyle
        ).joinToString("") { it.alp.toString() }

    sealed interface MbtiType {
        val alp: Char

        sealed interface Energy : MbtiType {
            object Extroversion : Energy {
                override val alp: Char = 'E'
            }

            object Introversion : Energy {
                override val alp: Char = 'I'
            }
        }

        sealed interface Information : MbtiType {
            object Sensing : Information {
                override val alp: Char = 'S'
            }

            object Intuition : Information {
                override val alp: Char = 'N'
            }
        }

        sealed interface Decision : MbtiType {
            object Thinking : Decision {
                override val alp: Char = 'T'
            }

            object Feeling : Decision {
                override val alp: Char = 'F'
            }
        }

        sealed interface Lifestyle : MbtiType {
            object Judging : Lifestyle {
                override val alp: Char = 'J'
            }

            object Perceiving : Lifestyle {
                override val alp: Char = 'P'
            }
        }
    }
}

fun createMbtiOrNull(mbtiTypeList: List<Mbti.MbtiType>): Mbti? {
    mbtiTypeList.run {
        val energy = filterIsInstance<Mbti.MbtiType.Energy>().firstOrNull()
        val information = filterIsInstance<Mbti.MbtiType.Information>().firstOrNull()
        val decision = filterIsInstance<Mbti.MbtiType.Decision>().firstOrNull()
        val lifestyle = filterIsInstance<Mbti.MbtiType.Lifestyle>().firstOrNull()
        return safeLet(
            energy,
            information,
            decision,
            lifestyle
        ) { e, i, d, l ->
            Mbti(
                energy = e,
                information = i,
                decision = d,
                lifestyle = l
            )
        }
    }
}

