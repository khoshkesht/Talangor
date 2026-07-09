package com.talangor.app.data.local.seed

import com.talangor.app.data.local.entity.ActionEntity

object InitialActionSeeds {
    val actions: List<ActionEntity> = listOf(
        ActionEntity(
            title = "سه نفس آرام",
            description = "سه بار آرام نفس بکش؛ دم را تا چهار بشمار و بازدم را کمی طولانی‌تر کن.",
            mood = "anxious",
            energyLevel = "low",
            durationMinutes = 1,
            category = "تنفس"
        ),
        ActionEntity(
            title = "لمس زمین",
            description = "کف پاها را روی زمین حس کن و نام سه چیزی را که می‌بینی در ذهن بگو.",
            mood = "anxious",
            energyLevel = "low",
            durationMinutes = 2,
            category = "زمین‌گیری"
        ),
        ActionEntity(
            title = "یک لیوان آب",
            description = "یک لیوان آب را آهسته بنوش و فقط روی حس خنکی آن تمرکز کن.",
            mood = "anxious",
            energyLevel = "low",
            durationMinutes = 2,
            category = "مراقبت فوری"
        ),
        ActionEntity(
            title = "یادداشت نگرانی",
            description = "نگرانی اصلی را در یک جمله بنویس و کنار آن فقط قدم بعدی کوچک را اضافه کن.",
            mood = "anxious",
            energyLevel = "medium",
            durationMinutes = 5,
            category = "نوشتن"
        ),
        ActionEntity(
            title = "کشش شانه",
            description = "شانه‌ها را بالا ببر، سه ثانیه نگه دار و آرام رها کن؛ پنج بار تکرار کن.",
            mood = "anxious",
            energyLevel = "medium",
            durationMinutes = 3,
            category = "حرکت سبک"
        ),
        ActionEntity(
            title = "مرتب کردن یک گوشه",
            description = "فقط یک سطح کوچک مثل میز یا کنار تخت را مرتب کن.",
            mood = "anxious",
            energyLevel = "medium",
            durationMinutes = 7,
            category = "محیط"
        ),
        ActionEntity(
            title = "پیاده‌روی سریع کوتاه",
            description = "سه تا پنج دقیقه با قدم‌های تند راه برو و نگاهت را از صفحه جدا نگه دار.",
            mood = "anxious",
            energyLevel = "high",
            durationMinutes = 5,
            category = "حرکت"
        ),
        ActionEntity(
            title = "تخلیه روی کاغذ",
            description = "هر فکری که می‌آید را بدون ویرایش برای پنج دقیقه بنویس.",
            mood = "anxious",
            energyLevel = "high",
            durationMinutes = 5,
            category = "نوشتن"
        ),
        ActionEntity(
            title = "دوش کوتاه",
            description = "یک دوش کوتاه بگیر یا فقط صورتت را با آب خنک بشوی.",
            mood = "anxious",
            energyLevel = "high",
            durationMinutes = 7,
            category = "تنظیم بدن"
        ),
        ActionEntity(
            title = "تغییر صندلی",
            description = "جایت را عوض کن و دو دقیقه از یک زاویه تازه به اتاق نگاه کن.",
            mood = "bored",
            energyLevel = "low",
            durationMinutes = 2,
            category = "تغییر محیط"
        ),
        ActionEntity(
            title = "یک عکس ساده",
            description = "از یک چیز معمولی اطرافت عکس بگیر و یک جزئیات تازه در آن پیدا کن.",
            mood = "bored",
            energyLevel = "low",
            durationMinutes = 3,
            category = "خلاقیت"
        ),
        ActionEntity(
            title = "انتخاب تصادفی",
            description = "یک کار کوچک عقب‌افتاده را تصادفی انتخاب کن و فقط دو دقیقه شروعش کن.",
            mood = "bored",
            energyLevel = "low",
            durationMinutes = 2,
            category = "شروع کوچک"
        ),
        ActionEntity(
            title = "پنج خط خواندن",
            description = "از یک کتاب، مقاله یا یادداشت فقط پنج خط بخوان و یک نکته بردار.",
            mood = "bored",
            energyLevel = "medium",
            durationMinutes = 5,
            category = "یادگیری"
        ),
        ActionEntity(
            title = "لیست سه ایده",
            description = "سه ایده برای بهتر کردن امروزت بنویس؛ لازم نیست عالی باشند.",
            mood = "bored",
            energyLevel = "medium",
            durationMinutes = 4,
            category = "خلاقیت"
        ),
        ActionEntity(
            title = "آهنگ تازه",
            description = "یک آهنگ تازه پخش کن و همزمان یک کار خیلی کوچک انجام بده.",
            mood = "bored",
            energyLevel = "medium",
            durationMinutes = 6,
            category = "انرژی"
        ),
        ActionEntity(
            title = "چالش ده دقیقه‌ای",
            description = "یک کار مشخص انتخاب کن و تایمر ده دقیقه‌ای بگذار؛ فقط تا پایان تایمر ادامه بده.",
            mood = "bored",
            energyLevel = "high",
            durationMinutes = 10,
            category = "چالش"
        ),
        ActionEntity(
            title = "یادگیری یک میانبر",
            description = "یک میانبر یا نکته کاربردی در ابزار کاری‌ات یاد بگیر و همان لحظه امتحان کن.",
            mood = "bored",
            energyLevel = "high",
            durationMinutes = 8,
            category = "یادگیری"
        ),
        ActionEntity(
            title = "مرتب‌سازی سریع",
            description = "ده چیز کوچک را جابه‌جا یا مرتب کن و بعد توقف کن.",
            mood = "bored",
            energyLevel = "high",
            durationMinutes = 7,
            category = "محیط"
        ),
        ActionEntity(
            title = "بستن چشم‌ها",
            description = "چشم‌ها را ببند، فک را رها کن و یک دقیقه هیچ کاری نکن.",
            mood = "tired",
            energyLevel = "low",
            durationMinutes = 1,
            category = "استراحت"
        ),
        ActionEntity(
            title = "آب و نور",
            description = "آب بنوش و اگر ممکن است دو دقیقه کنار نور طبیعی بایست.",
            mood = "tired",
            energyLevel = "low",
            durationMinutes = 3,
            category = "مراقبت بدن"
        ),
        ActionEntity(
            title = "کاهش بار",
            description = "یک کار غیرضروری امروز را حذف یا به بعد موکول کن.",
            mood = "tired",
            energyLevel = "low",
            durationMinutes = 2,
            category = "برنامه‌ریزی"
        ),
        ActionEntity(
            title = "کشش گردن",
            description = "گردن را آرام به چپ و راست خم کن و هر سمت سه نفس بمان.",
            mood = "tired",
            energyLevel = "medium",
            durationMinutes = 4,
            category = "حرکت سبک"
        ),
        ActionEntity(
            title = "میان‌وعده سبک",
            description = "اگر گرسنه‌ای یک میان‌وعده سبک و ساده آماده کن.",
            mood = "tired",
            energyLevel = "medium",
            durationMinutes = 5,
            category = "مراقبت بدن"
        ),
        ActionEntity(
            title = "کار تک‌مرحله‌ای",
            description = "فقط یک مرحله از یک کار را انجام بده؛ مثلا فقط فایل را باز کن یا فقط عنوان را بنویس.",
            mood = "tired",
            energyLevel = "medium",
            durationMinutes = 5,
            category = "شروع کوچک"
        ),
        ActionEntity(
            title = "پیاده‌روی آرام",
            description = "هفت دقیقه آرام راه برو و تلاش نکن سرعت بگیری.",
            mood = "tired",
            energyLevel = "high",
            durationMinutes = 7,
            category = "حرکت"
        ),
        ActionEntity(
            title = "مرتب کردن بدن",
            description = "صورتت را بشوی، لباس راحت‌تر بپوش یا وضعیت نشستن را اصلاح کن.",
            mood = "tired",
            energyLevel = "high",
            durationMinutes = 6,
            category = "تنظیم بدن"
        ),
        ActionEntity(
            title = "استراحت زمان‌دار",
            description = "تایمر ده دقیقه‌ای بگذار و بدون صفحه نمایش استراحت کن.",
            mood = "tired",
            energyLevel = "high",
            durationMinutes = 10,
            category = "استراحت"
        ),
        ActionEntity(
            title = "حذف یک حواس‌پرتی",
            description = "فقط یک اعلان، تب یا وسیله مزاحم را از جلوی چشم بردار.",
            mood = "unfocused",
            energyLevel = "low",
            durationMinutes = 2,
            category = "تمرکز"
        ),
        ActionEntity(
            title = "یک جمله هدف",
            description = "در یک جمله بنویس الان دقیقا می‌خواهی چه کاری انجام بدهی.",
            mood = "unfocused",
            energyLevel = "low",
            durationMinutes = 2,
            category = "شفاف‌سازی"
        ),
        ActionEntity(
            title = "تایمر سه دقیقه",
            description = "سه دقیقه فقط روی یک کار بمان؛ بعد اجازه داری تصمیم بگیری ادامه بدهی یا نه.",
            mood = "unfocused",
            energyLevel = "low",
            durationMinutes = 3,
            category = "تمرکز"
        ),
        ActionEntity(
            title = "تقسیم کار",
            description = "کار فعلی را به سه قدم کوچک تقسیم کن و قدم اول را علامت بزن.",
            mood = "unfocused",
            energyLevel = "medium",
            durationMinutes = 5,
            category = "برنامه‌ریزی"
        ),
        ActionEntity(
            title = "پاکسازی میز",
            description = "فقط چیزهایی را که برای کار بعدی لازم نیست از میز دور کن.",
            mood = "unfocused",
            energyLevel = "medium",
            durationMinutes = 5,
            category = "محیط"
        ),
        ActionEntity(
            title = "حالت تمام‌صفحه",
            description = "برنامه یا سند اصلی را تمام‌صفحه کن و بقیه پنجره‌ها را پنهان کن.",
            mood = "unfocused",
            energyLevel = "medium",
            durationMinutes = 2,
            category = "تمرکز"
        ),
        ActionEntity(
            title = "اسپرینت کوتاه",
            description = "ده دقیقه کار متمرکز انجام بده و فقط خروجی کوچک بساز.",
            mood = "unfocused",
            energyLevel = "high",
            durationMinutes = 10,
            category = "تمرکز"
        ),
        ActionEntity(
            title = "بلند خواندن کار",
            description = "کاری را که باید انجام دهی با صدای آرام بخوان و همان لحظه شروع کن.",
            mood = "unfocused",
            energyLevel = "high",
            durationMinutes = 3,
            category = "شروع کوچک"
        ),
        ActionEntity(
            title = "یادداشت مزاحمت‌ها",
            description = "هر فکر مزاحم را در یک لیست پارک کن تا بعدا سراغش بروی.",
            mood = "unfocused",
            energyLevel = "high",
            durationMinutes = 5,
            category = "نوشتن"
        ),
        ActionEntity(
            title = "فقط باز کردن",
            description = "فقط فایل، ابزار یا صفحه مربوط به کار را باز کن؛ همین کافی است.",
            mood = "unmotivated",
            energyLevel = "low",
            durationMinutes = 1,
            category = "شروع کوچک"
        ),
        ActionEntity(
            title = "چرا کوچک",
            description = "یک دلیل کوتاه بنویس که چرا انجام همین قدم کوچک به نفعت است.",
            mood = "unmotivated",
            energyLevel = "low",
            durationMinutes = 3,
            category = "انگیزه"
        ),
        ActionEntity(
            title = "پاداش کوچک",
            description = "یک پاداش خیلی کوچک برای بعد از انجام قدم اول مشخص کن.",
            mood = "unmotivated",
            energyLevel = "low",
            durationMinutes = 2,
            category = "انگیزه"
        ),
        ActionEntity(
            title = "قدم دو دقیقه‌ای",
            description = "کاری را انتخاب کن که در دو دقیقه قابل شروع است و فقط همان را انجام بده.",
            mood = "unmotivated",
            energyLevel = "medium",
            durationMinutes = 2,
            category = "شروع کوچک"
        ),
        ActionEntity(
            title = "پیام پاسخ‌گویی",
            description = "به یک نفر بگو تا ده دقیقه دیگر یک قدم کوچک انجام می‌دهی.",
            mood = "unmotivated",
            energyLevel = "medium",
            durationMinutes = 4,
            category = "حمایت"
        ),
        ActionEntity(
            title = "دیدن پیشرفت",
            description = "یک کار تمام‌شده قبلی را به خودت یادآوری کن و یک خط درباره‌اش بنویس.",
            mood = "unmotivated",
            energyLevel = "medium",
            durationMinutes = 5,
            category = "انگیزه"
        ),
        ActionEntity(
            title = "تعهد ده دقیقه‌ای",
            description = "ده دقیقه روی کار بمان و بعد بدون قضاوت تصمیم بگیر ادامه بدهی یا توقف کنی.",
            mood = "unmotivated",
            energyLevel = "high",
            durationMinutes = 10,
            category = "تعهد"
        ),
        ActionEntity(
            title = "شروع با موسیقی",
            description = "یک آهنگ پرانرژی بگذار و تا پایان آن فقط قدم اول را انجام بده.",
            mood = "unmotivated",
            energyLevel = "high",
            durationMinutes = 5,
            category = "انرژی"
        ),
        ActionEntity(
            title = "حذف اصطکاک",
            description = "یک مانع کوچک را حذف کن؛ مثل آماده کردن ابزار، بستن تب اضافه یا مرتب کردن فایل‌ها.",
            mood = "unmotivated",
            energyLevel = "high",
            durationMinutes = 8,
            category = "آماده‌سازی"
        )
    )
}
