package com.talangor.app.domain.usecase

import com.talangor.app.core.model.MoodOption
import com.talangor.app.domain.model.ActionSuggestion

class GetMoodActionsUseCase {
    operator fun invoke(mood: MoodOption): List<ActionSuggestion> {
        return when (mood) {
            MoodOption.Calm -> listOf(
                ActionSuggestion("ثبت یک ایده", "سه دقیقه وقت بگذار و یک ایده کوچک برای امروز بنویس."),
                ActionSuggestion("مرور آرام", "یکی از کارهای نیمه‌تمام را بدون عجله مرتب کن.")
            )

            MoodOption.Tired -> listOf(
                ActionSuggestion("استراحت کوتاه", "ده دقیقه از صفحه فاصله بگیر و آب بنوش."),
                ActionSuggestion("کار سبک", "فقط یک کار کوچک و کم‌فشار را انتخاب کن.")
            )

            MoodOption.Anxious -> listOf(
                ActionSuggestion("تنفس ۴-۴", "چهار ثانیه نفس بکش، چهار ثانیه نگه دار، چهار بار تکرار کن."),
                ActionSuggestion("کوچک‌سازی کار", "کار بعدی را به یک قدم خیلی کوچک تبدیل کن.")
            )

            MoodOption.Sad -> listOf(
                ActionSuggestion("ارتباط امن", "برای یک نفر قابل اعتماد یک پیام کوتاه بفرست."),
                ActionSuggestion("حرکت ملایم", "پنج دقیقه راه برو یا بدن را آرام کشش بده.")
            )

            MoodOption.Energized -> listOf(
                ActionSuggestion("شروع عمیق", "بیست دقیقه روی مهم‌ترین کار امروز تمرکز کن."),
                ActionSuggestion("پاکسازی سریع", "یک فضای کوچک یا لیست کارها را مرتب کن.")
            )
        }
    }
}
