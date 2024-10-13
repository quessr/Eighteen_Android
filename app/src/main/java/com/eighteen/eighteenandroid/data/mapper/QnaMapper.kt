package com.eighteen.eighteenandroid.data.mapper

import com.eighteen.eighteenandroid.data.datasource.remote.response.QuestionResponse
import com.eighteen.eighteenandroid.domain.model.Qna
import com.eighteen.eighteenandroid.domain.model.QnaType

object QnaMapper {
    //TODO 질문 내용 매핑 추가
    fun QuestionResponse.toQna(): Qna? {
        return Qna(
            question = QnaType.values().find { it.name == question } ?: return null,
            answer = answer
        )
    }
}