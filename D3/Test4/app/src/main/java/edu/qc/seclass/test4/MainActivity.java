package edu.qc.seclass.test4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> TotalList = new ArrayList<Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> unit = new ArrayList<String>();
        unit.add("$1.00/Single");
        unit.add("$2.99/LB");

        Item i1 = new Item(false, "apple", unit,2 );
        Item i2 = new Item(false, "peach", unit, 2 );
        Item i3 = new Item(true, "banana", unit, 2 );
        Item i4 = new Item(true, "apple", unit, 2 );
        TotalList.add(i1);
        TotalList.add(i2);
        TotalList.add(i3);
        TotalList.add(i4);

        ArrayList<Item> uncheckedlist = new ArrayList<Item>();
        for(Item i: TotalList){
            if(i.check == false)
                uncheckedlist.add(i);
        }

        UncheckedAdapter uca = new UncheckedAdapter(this, uncheckedlist);
        ListView unchecked = (ListView) findViewById(R.id.uncheckedlist);
        unchecked.setAdapter(uca);

    }
    public class UncheckedAdapter extends ArrayAdapter<Item> implements AdapterView.OnItemSelectedListener {
        public UncheckedAdapter(Context context, ArrayList<Item> items) {
            super(context, 0, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final ViewHolder holder;
            final Item it = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_inlist, parent, false);
                holder.checkbox = convertView.findViewById(R.id.cb);
                holder.name = convertView.findViewById(R.id.item_name);
                holder.quatity = convertView.findViewById(R.id.item_quantity);
                holder.price = convertView.findViewById(R.id.item_price);
                holder.total = convertView.findViewById(R.id.item_total);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.checkbox.setChecked(it.check);
            holder.name.setText(it.itemName);
            holder.quatity.setText(""+it.itemQuantity);
            holder.quatity.setId(position);
            holder.quatity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        final int position = v.getId();
                        final EditText quatity = (EditText) v;
                        it.itemQuantity = Double.parseDouble(quatity.getText().toString());
                        String s = holder.price.getSelectedItem().toString();
                        String asd[] = s.split("/");
                        s = asd[0].substring(1);
                        Double pr = Double.parseDouble(s);
                        Double qty = Double.parseDouble(holder.quatity.getText().toString());
                        holder.total.setText("$"+pr*qty);
                    }
                }
            });
            holder.price.setOnItemSelectedListener(this);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, it.itemPrice);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.price.setAdapter(arrayAdapter);

            String s = holder.price.getSelectedItem().toString();
            String asd[] = s.split("/");
            s = asd[0].substring(1);
            Double pr = Double.parseDouble(s);
            Double qty = Double.parseDouble(holder.quatity.getText().toString());
            holder.total.setText("$"+pr*qty);
            // Showing selected spinner item

            return convertView;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    class ViewHolder {
        CheckBox checkbox;
        TextView name;
        Spinner price;
        EditText quatity;
        TextView total;
    }
}