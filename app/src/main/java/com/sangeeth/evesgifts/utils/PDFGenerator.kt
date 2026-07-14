package com.sangeeth.evesgifts.utils

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.intl.Locale
import androidx.core.content.FileProvider
import com.sangeeth.evesgifts.R
import com.sangeeth.evesgifts.data.SelectedCake
import com.sangeeth.evesgifts.data.SelectedFrame
import com.sangeeth.evesgifts.data.SelectedGifts
import android.graphics.*
import android.icu.text.SimpleDateFormat
import android.provider.CalendarContract
import java.util.Date


fun PDFGenerator(
    context: Context,
    quotationId: String,
    customerName: String,
    frames: List<SelectedFrame>,
    cakes: List<SelectedCake>,
    gifts: List<SelectedGifts>,
    totalPrice: Double
){

    val pdfDocument = PdfDocument()

    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas

    val titlePaint = android.graphics.Paint().apply {
        textSize = 22f
        color = android.graphics.Color.BLACK
        typeface = Typeface.DEFAULT_BOLD
    }

    val headerPaint = android.graphics.Paint().apply {
        textSize = 12f
        color = android.graphics.Color.BLACK
        typeface = Typeface.DEFAULT_BOLD
    }

    val textPaint = android.graphics.Paint().apply {
        textSize = 22f
        color = android.graphics.Color.BLACK
    }

    val linePaint = android.graphics.Paint().apply {
        strokeWidth = 1f
        color = android.graphics.Color.LTGRAY
    }

    var y = 50f

    canvas.drawText("Eve's Gifts", 200f, y, titlePaint)

    y += 30
    canvas.drawText(
        "Quotation #: $quotationId",
        40f,
        y,
        textPaint
    )

    y += 20
    canvas.drawText(
        "Customer: $customerName", 40f, y, textPaint
    )

    y += 20
    canvas.drawText(
        "Date: ${SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(Date())}",
        40f,
        y,
        textPaint

    )

    y += 30
    canvas.drawLine(40f, y, 555f, y, linePaint)

    y += 20

    canvas.drawText("Item", 40f, y, headerPaint)
    canvas.drawText("Type", 230f, y, headerPaint)
    canvas.drawText("QTY", 360f, y, headerPaint)
    canvas.drawText("Price", 500f, y, headerPaint)


    y += 10
    canvas.drawLine(40f, y, 555f, y, linePaint)

    y += 20

    frames.forEach { frame ->
        canvas.drawText(frame.category, 40f, y, textPaint)
        canvas.drawText(frame.size, 230f, y, textPaint)
        canvas.drawText(frame.quantity.toString(), 360f, y, textPaint)

        val total = (frame.price?.toDouble() ?: 0.0) * frame.quantity
        canvas.drawText("Rs. %.2f".format(total), 470f, y, textPaint)
    }
}