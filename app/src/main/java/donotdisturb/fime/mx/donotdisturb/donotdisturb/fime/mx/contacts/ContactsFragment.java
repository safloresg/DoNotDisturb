package donotdisturb.fime.mx.donotdisturb.donotdisturb.fime.mx.contacts;


import android.app.LauncherActivity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v7.view.menu.ListMenuItemView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AlphabetIndexer;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.Locale;

import donotdisturb.fime.mx.donotdisturb.R;


public class ContactsFragment
        extends ListFragment implements AdapterView.OnItemClickListener , LoaderManager.LoaderCallbacks<Cursor>
{

    private boolean[] checkedBoxesBool;

    private  static final String STATE_PREVIOUSLY_SELECTED_KEY = "donotdisturb.fime.mx.SELECTED_ITEM";
    private String mSearchTerm;
    private final static String[] FROM_COLUMNS =
            {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME

            };
    private  static int [] TO_IDS =
            {};
    private ListView mContactList;
    private long mContactID;
    private String mContactKey;
    private Uri mContactUri;
    ContactsAdapter mAdapter;
    private static ContactManager cm;
    private int mPreviouslySelectedSearchItem = 0;
    private boolean mSearchQueryChanged;

    public ContactsFragment()
    {
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        cm = new ContactManager(getActivity());

        mAdapter = new ContactsAdapter(getActivity());
        if (savedInstanceState != null)
        {
            mSearchTerm = savedInstanceState.getString(SearchManager.QUERY);
            mPreviouslySelectedSearchItem = savedInstanceState.getInt(STATE_PREVIOUSLY_SELECTED_KEY);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        

        return super.onCreateView(inflater,parent,savedInstanceState);

    }

    @Override
    public void onPause()
    {
        super.onPause();
        cm.updateIsSelectedState(checkedBoxesBool,mAdapter.getCursor());
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        checkedBoxesBool[position] = !checkedBoxesBool[position];

        Log.d("OnClick", Integer.toString(position) + " " + Boolean.toString(checkedBoxesBool[position]));
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onActivityCreated (Bundle savedInstanceState)
    {
        super.onActivityCreated(null);
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(this);
        getLoaderManager().initLoader(ContactsQuery.QUERY_ID, null, this);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.contact_list_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);

        final SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        String newFilter = !TextUtils.isEmpty(newText) ? newText : null;

                        if (mSearchTerm == null && newFilter == null) {
                            return true;
                        }

                        if (mSearchTerm != null && mSearchTerm.equals(newFilter)) {
                            return true;

                        }

                        mSearchTerm = newFilter;
                        mSearchQueryChanged = true;

                        getLoaderManager().restartLoader(ContactsQuery.QUERY_ID, null, ContactsFragment.this);

                        return true;

                    }
                }
        );
        if (mSearchTerm != null)
        {
            final String savedSearchTerm = mSearchTerm;
            searchItem.expandActionView();

            searchView.setQuery(savedSearchTerm, false);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if(!TextUtils.isEmpty(mSearchTerm))
        {
            outState.putString(SearchManager.QUERY, mSearchTerm);

            outState.putInt(STATE_PREVIOUSLY_SELECTED_KEY, getListView().getCheckedItemPosition());

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_search:
                getActivity().onSearchRequested();
                break;

            case R.id.update_contacts:
                updateContacts();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    /*Read contacts from the android agenda and update the contact table with retrieved data.*/
    private void updateContacts()
    {
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        cm.deleteAllContacts();
        if (cur !=null && cur.getCount() > 0)
        {
            Contact contact = null;
            while(cur.moveToNext())
            {
                contact = new Contact();
                contact.setName(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                cm.addContact(contact);
            }
            getLoaderManager().restartLoader(1,null,this);
        }
    }

    /*==================================================================================*/
//                          START LOADER CALLBACKS SECTION
/*==================================================================================*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

   /*     if (id == ContactsQuery.QUERY_ID)
        {
            Uri contentUri;
            if (mSearchTerm == null)
            {

                contentUri = ContactsQuery.CONTENT_URI;
            }
            else
            {
                contentUri = Uri.withAppendedPath(ContactsQuery.FILTER_URI,Uri.encode(mSearchTerm));
            }
            return new CursorLoader(getActivity()
                    ,contentUri,
                    ContactsQuery.PROJECTION,
                    ContactsQuery.SELECTION,
                    null,
                    ContactsQuery.SORT_ORDER);

        }
    return  null;*/
        return  new ContactListCursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == ContactsQuery.QUERY_ID)
        {
            mAdapter.swapCursor(data);

            checkedBoxesBool = new boolean[data.getCount()];
            //populate the checkedBoxesBool
            AndQuietDBHelper.ContactCursor contactData = (AndQuietDBHelper.ContactCursor)data;
            contactData.moveToFirst();
            for (int i=0; i <= data.getCount() -1; i++)
            {
                checkedBoxesBool[i] = contactData.getcontact().isSelected();
                contactData.moveToNext();
            }

            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /*==================================================================================*/
//                          END LOADER CALLBACKS SECTION
/*==================================================================================*/

    private class ContactsAdapter extends CursorAdapter
    {

        LayoutInflater mInflater;
        private TextAppearanceSpan highlightTextSpan;
        private AlphabetIndexer mAlphabetIndexer;


        public ContactsAdapter(Context context) {
            super(context,null,0);

            mInflater = LayoutInflater.from(context);
            highlightTextSpan = new TextAppearanceSpan(getActivity(),R.style.AppTheme);

            //mAlphabetIndexer = new AlphabetIndexer(null,ContactsQuery.SORT_KEY,alp);
        }

        @Override
        public View newView(Context context, final Cursor cursor, ViewGroup parent) {
            final View itemLayout = mInflater.inflate(R.layout.contact_list_item,parent,false);

            final ViewHolder holder = new ViewHolder();
            holder.text1 = (TextView) itemLayout.findViewById(android.R.id.text1);
            holder.text2 = (TextView) itemLayout.findViewById(android.R.id.text2);
            holder.checkBox = (CheckBox) itemLayout.findViewById(android.R.id.checkbox);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
            itemLayout.setTag(holder);
            return itemLayout;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor)
        {

            final ViewHolder holder = (ViewHolder)view.getTag();
         //   holder.checkBox.setChecked(false);
            final String displayName = cursor.getString(cursor.getColumnIndex("name"));

            final int startIndex = indexOfSearchQuery(displayName);

            if (checkedBoxesBool == null || checkedBoxesBool.length != cursor.getCount())
            {
                checkedBoxesBool = new boolean[cursor.getCount()];
            }

            if (checkedBoxesBool[cursor.getPosition()])
                Log.d("Bind",Integer.toString(cursor.getPosition()));
            holder.checkBox.setChecked(checkedBoxesBool[cursor.getPosition()]);
            if (startIndex == -1)
            {
                holder.text1.setText(displayName);

                if (TextUtils.isEmpty(mSearchTerm))
                {
                    holder.text2.setVisibility(View.GONE);
                }
                else
                {
                    holder.text2.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                final SpannableString highlightedName = new SpannableString(displayName);
                highlightedName.setSpan(highlightTextSpan,startIndex,startIndex+mSearchTerm.length(),0);

                holder.text1.setText(highlightedName);

                holder.text2.setVisibility(View.GONE);
            }

        }



        private int indexOfSearchQuery(String displayName) {
            if (!TextUtils.isEmpty(mSearchTerm)) {
                return displayName.toLowerCase(Locale.getDefault()).indexOf(
                        mSearchTerm.toLowerCase(Locale.getDefault()));
            }
            return -1;
        }
    }

    private class ViewHolder
    {
        TextView text1;
        TextView text2;
        QuickContactBadge icon;
        CheckBox checkBox;
    }

    public interface ContactsQuery
    {
        final static int QUERY_ID = 1;

        final static Uri FILTER_URI = ContactsContract.Contacts.CONTENT_FILTER_URI;

        final static Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;

        final static String SELECTION = ContactsContract.Contacts.DISPLAY_NAME + "<>''" + " AND " + ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1";

        final static String SORT_ORDER = ContactsContract.Contacts.SORT_KEY_PRIMARY;

        final static String[] PROJECTION = {

                ContactsContract.Contacts._ID,

                ContactsContract.Contacts.LOOKUP_KEY,

                ContactsContract.Contacts.DISPLAY_NAME,

                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,

                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,

                SORT_ORDER
        };

        final static int ID =0;
        final static int LOOKUP_KEY = 1;
        final static int DISPLAY_NAME_PRIMARY=2;
        final static int PHOTO_THUMBNAIL_DATA=3;
        final static int SORT_KEY=4;

    }

    private static class ContactListCursorLoader extends SQLiteCursorLoader
    {

        public ContactListCursorLoader(Context context) {
            super(context);
        }

        @Override
        protected Cursor loadCursor() {
            return cm.getContacts();
        }
    }


}
