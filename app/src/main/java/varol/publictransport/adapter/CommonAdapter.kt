package varol.publictransport.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_stop_list.view.iv_icon_LayoutStop
import kotlinx.android.synthetic.main.layout_stop_list.view.tv_title_LayoutStop
import kotlinx.android.synthetic.main.layout_travel_modes_list.view.cv_allRoutes_LayoutTravelModes
import kotlinx.android.synthetic.main.layout_travel_modes_list.view.iv_allRoutes_LayoutTravelModes
import kotlinx.android.synthetic.main.layout_travel_modes_list.view.tv_allRoutes_LayoutTravelModes
import varol.publictransport.R
import varol.publictransport.data.model.Route
import varol.publictransport.data.model.TravelMode
import varol.publictransport.util.loadByDrawable
import varol.publictransport.util.loadByUrl
import varol.publictransport.util.toColor

/**
 * Created by Murat on 30.12.2017.
 */
class CommonAdapter<T> : RecyclerView.Adapter<CommonAdapter.ViewHolder> {

  object ItemType {
    val ROUTE = 0
    val TRAVEL_MODE = 1
  }

  private var mContext: Context

  constructor(context: Context) {
    mContext = context

  }

  private val items: ArrayList<T> = arrayListOf()

  interface OnRouteClickListener {
    fun onItemClick(position: Int)
  }
  interface OnTravelModeClickListener {
    fun onItemClick(position: Int)
  }

  private var onRouteClickListener: OnRouteClickListener? = null
  private var onTravelModeClickListener: OnTravelModeClickListener? = null


  override fun getItemViewType(position: Int): Int {
    return if(items[position] is Route)
    {
      ItemType.ROUTE
    }else // if (items[position] is )
    {
      ItemType.TRAVEL_MODE
    }
  }


  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

    when (viewType) {

      ItemType.ROUTE -> {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_stop_list, parent,
            false)
        return ViewHolder(view)
      }

      ItemType.TRAVEL_MODE -> {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_travel_modes_list, parent,
            false)
        return ViewHolder(view)
      }

      else ->
      {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.layout_stop_list, parent,
            false)
        return ViewHolder(view)
      }

    }


  }

  override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

    holder?.let {
      when (getItemViewType(position)) {
        ItemType.ROUTE -> {

          holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION && onRouteClickListener != null) {
              onRouteClickListener!!.onItemClick(holder.adapterPosition)
            }
          }
          holder.bindRoutesList(items[position] as Route, mContext)

        }
        ItemType.TRAVEL_MODE ->
        {

          holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION && onTravelModeClickListener != null) {
              onTravelModeClickListener!!.onItemClick(holder.adapterPosition)
            }
          }

          holder.bindTravelModesList(items[position] as TravelMode, mContext)
        }
      }
    }
  }

  class ViewHolder(itemView: View) :
      RecyclerView.ViewHolder(itemView) {


    fun bindRoutesList(item: Route, context: Context) {

      try {
        val travelMode = item.segments!![0].travel_mode ?:""

        when(travelMode)
        {
//        TravelModes.DRIVING->   {      }
//        TravelModes.BUS->   {      }
//        TravelModes.SUBWAY->   {      }
//        TravelModes.WALKING->   {      }
//        TravelModes.CYCLING ->{     }

        }
      }catch (e:Exception)
      {
        // TODO: HANDLE EXCEPTION
      }


      itemView.iv_icon_LayoutStop.loadByDrawable(context, R.drawable.ic_gps_off)
      itemView.tv_title_LayoutStop.text = item.provider ?: ""
    }


    fun bindTravelModesList(travelMode: TravelMode, mContext: Context) {

      itemView.iv_allRoutes_LayoutTravelModes.loadByUrl(mContext,travelMode.icon_url?:"")
      /**
       * replace "_" to " " and capitalize letter;
       * ex.: "bus_travelling" to "Bus travelling" for better listing
       * */
      itemView.tv_allRoutes_LayoutTravelModes.text = travelMode.travel_mode?.replace("_"," ")?.capitalize()
      itemView.cv_allRoutes_LayoutTravelModes.setCardBackgroundColor(travelMode.color?.toColor()?:
          ContextCompat.getColor(mContext, R.color.colorPrimary))

    }




  }




  override fun getItemCount(): Int {
    return items.size
  }


  override fun getItemId(position: Int): Long {
    return super.getItemId(position)
  }



  //region Custom helper methods

  fun isEmpty(): Boolean {
    return itemCount == 0
  }

  fun clear() {
    while (itemCount > 0) {
      items.removeAt(itemCount - 1)
      this.notifyItemRemoved(itemCount - 1)
    }
  }

  fun add(item: T) {
    items.add(item)
    notifyItemInserted(items.size - 1)
  }

  fun addAt(position: Int, item: T) {
    items.add(position, item)
    notifyItemInserted(position)
  }

  fun addAll(itemList: List<T>) {
    this.items.addAll(itemList)
    notifyItemRangeChanged((items.size - itemList.size), itemList.size)
  }

  fun remove(item: T) {
    val position = items.indexOf(item)
    if (position > -1) {
      items.removeAt(position)
      notifyItemRemoved(position)
    }
  }

  fun getItemTravelMode(position: Int): String?{
    if(items[position] is TravelMode)
    {
      return (items[position] as TravelMode).travel_mode
    }
    return null
  }



  fun setOnRouteItemClickListener(onRouteClickListener: OnRouteClickListener) {
    this.onRouteClickListener = onRouteClickListener
  }
  fun setOnTravelModeItemClickListener(onTravelModeClickListener: OnTravelModeClickListener) {
    this.onTravelModeClickListener = onTravelModeClickListener
  }

  //endregion
}