package at.ac.tuwien.mns.cellinfo

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import at.ac.tuwien.mns.cellinfo.fragments.CellListViewFragment
import at.ac.tuwien.mns.cellinfo.fragments.CellMapFragment
import at.ac.tuwien.mns.cellinfo.service.CellInfoService
import at.ac.tuwien.mns.cellinfo.service.impl.CellInfoServiceImpl
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

 
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        cellInfoService = CellInfoServiceImpl(this, getString(R.string.opencellid_key))

    }

    // No settings menu needed at the moment
    /*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }*/


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val tabs: List<Fragment> = listOf(
                CellListViewFragment.newInstance(1),
                CellMapFragment.newInstance(2))

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return tabs[position]
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }
    }

    companion object {
        var cellInfoService: CellInfoService? = null
    }
}
