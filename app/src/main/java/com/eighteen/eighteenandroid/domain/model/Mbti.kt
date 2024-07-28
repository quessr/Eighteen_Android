package com.eighteen.eighteenandroid.domain.model

data class Mbti(
    val energy: MbtiType.Energy,
    val information: MbtiType.Information,
    val decision: MbtiType.Decision,
    val lifestyle: MbtiType.Lifestyle
) {
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

