import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ui.adapters.ClientAdapter


class SwipeToDeleteCallback(private val adapter: ClientAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val paint = Paint()

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val clientId = adapter.clients[position].id
        adapter.listener.onItemDismiss(clientId)
        adapter.notifyItemRemoved(position)
    }

    override fun onChildDraw(
        canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        paint.color = Color.RED

        canvas.drawRect(
            itemView.right.toFloat() + dX, itemView.top.toFloat(),
            itemView.right.toFloat(), itemView.bottom.toFloat(), paint
        )

        paint.color = Color.WHITE
        paint.textSize = 40f
        val text = "Supprimer"
        val textWidth = paint.measureText(text)
        canvas.drawText(
            text, itemView.right - textWidth - 40,
            itemView.top + (itemHeight + paint.textSize) / 2, paint
        )

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}

