package com.example.projet_tdm_medcin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.projet_tdm_medcin.R
import com.example.projet_tdm_medcin.entity.Booking

import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendar
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import kotlinx.android.synthetic.main.fragment_appointments.*
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private var currentMonth = 0
    private val calendar = Calendar.getInstance()
    private val allBookings = mutableListOf<Booking>()

    private lateinit var pagerAdapter: BookingFragmentAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
            R.layout.fragment_appointments,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPager(view)
        initCalendar(view)
        attachObservers()
    }

    private fun attachObservers() {

       /* taskViewModel.getAllTaches(requireContext())?.observe(viewLifecycleOwner, Observer {
            allBookings.clear()
            allBookings.addAll(it)
        })*/

    }

    private fun initPager(view: View) {
        pagerAdapter = BookingFragmentAdapter(this)
        viewPager = view.findViewById(R.id.pagerDashboard)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = 1



    }

    private fun initCalendar(view: View) {

        calendar.time = Date()
        currentMonth = calendar[Calendar.MONTH]

        // Init view manager
        val myCalendarViewManager = object : CalendarViewManager {
            override fun setCalendarViewResourceId(
                position: Int,
                date: Date,
                isSelected: Boolean
            ): Int {
                // return item layout files, which you have created
                return if (!isSelected) {
                    R.layout.calendar_item_layout
                } else {
                    R.layout.calendar_item_selected_layout
                }
            }

            override fun bindDataToCalendarView(
                holder: SingleRowCalendarAdapter.CalendarViewHolder,
                date: Date,
                position: Int,
                isSelected: Boolean
            ) {
                // bind data to calendar item views
                val dayNumber = holder.itemView.findViewById<TextView>(R.id.textCalendarDayNumber)
                val dayName3 = holder.itemView.findViewById<TextView>(R.id.textCalendarDayName3)
                dayNumber.text = DateUtils.getDayNumber(date)
                dayName3.text = DateUtils.getDay3LettersName(date)
            }
        }

        // Init selection manager
        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                // return true if item can be selected
                return true
            }
        }

        // Setup observers
        val myCalendarChangesObserver = object : CalendarChangesObserver {

            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {
                pagerAdapter.updateTasksUI(getTasksByDate(date))
                super.whenSelectionChanged(isSelected, position, date)
            }
        }

        val calendarView:SingleRowCalendar = view.findViewById(R.id.calendarView)
        val day = calendar[Calendar.DAY_OF_WEEK_IN_MONTH]

        val cal = calendarView.apply {
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            initialPositionIndex = day
            includeCurrentDate = true
            setDates(getDatesOfCurrentMonth())
            init()
        }

        cal.select(day)

        buttonPreviousMonthDashboard.setOnClickListener {
            cal.setDates(getDatesOfPreviousMonth())
            textMonthDashboard.text = "${DateUtils.getMonthName(calendar.time)}, " +
                    "${DateUtils.getYear(calendar.time)}"
        }

        buttonNextMonthDashboard.setOnClickListener {
            cal.setDates(getDatesOfNextMonth())
            textMonthDashboard.text = "${DateUtils.getMonthName(calendar.time)}, " +
                    "${DateUtils.getYear(calendar.time)}"
        }

        textMonthDashboard.text = "${DateUtils.getMonthName(calendar.time)}, " +
                "${DateUtils.getYear(calendar.time)}"
    }

    private fun getTasksByDate(date: Date): List<Booking>{
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return allBookings.filter { sdf.format(date).equals(sdf.format(it.bookingDate)) }
    }

    private fun getDatesOfNextMonth(): List<Date> {
        currentMonth++ // + because we want next month
        if (currentMonth == 12) {
            // we will switch to january of next year, when we reach last month of year
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] + 1)
            currentMonth = 0 // 0 == january
        }
        return getDates(mutableListOf())
    }

    private fun getDatesOfPreviousMonth(): List<Date> {
        currentMonth-- // - because we want previous month
        if (currentMonth == -1) {
            // we will switch to december of previous year, when we reach first month of year
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] - 1)
            currentMonth = 11 // 11 == december
        }
        return getDates(mutableListOf())
    }

    private fun getDatesOfCurrentMonth(): List<Date> {
        if (currentMonth == -1) {
            // we will switch to december of previous year, when we reach first month of year
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] - 1)
            currentMonth = 11 // 11 == december
        }
        return getDates(mutableListOf())
    }

    private fun getFutureDatesOfCurrentMonth(): List<Date> {
        // get all next dates of current month
        currentMonth = calendar[Calendar.MONTH]
        return getDates(mutableListOf())
    }


    private fun getDates(list: MutableList<Date>): List<Date> {
        // load dates of whole month
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        list.add(calendar.time)
        while (currentMonth == calendar[Calendar.MONTH]) {
            calendar.add(Calendar.DATE, +1)
            if (calendar[Calendar.MONTH] == currentMonth)
                list.add(calendar.time)
        }
        calendar.add(Calendar.DATE, -1)
        return list
    }

    class BookingFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        private val doneTasksFragment = BookingPagerFragment()

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            // Return a NEW fragment instance in createFragment(int)
            return doneTasksFragment
        }

        fun updateTasksUI(tasks: List<Booking>) {
            doneTasksFragment.updateTasks(getDoneTasks(tasks))
        }


        private fun getDoneTasks(allBookings: List<Booking>): List<Booking> {
            return allBookings.filter { it.bookingDate != null }
        }
    }
}


