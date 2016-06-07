package com.lzm.Cajas.detail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.db.Especie;

public class DetailFragment extends Fragment {
    public static final String SPECIES_ID = "especieId";
    private MainActivity context;

    private Especie especie;
    private Long especieId;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Long speciesId) {
        System.out.println("..............................");
        System.out.println("NEW INSTANCE");
        System.out.println(speciesId);
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putLong(SPECIES_ID, speciesId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            especieId = getArguments().getLong(SPECIES_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        especie = Especie.get(context, especieId);

        TextView textView = (TextView) view.findViewById(R.id.textField);
        String str = "Under the bed hiss at vacuum cleaner. Chase ball of string kitty loves pigs yet pee in the shoe. Sleep on dog bed, force dog to sleep on floor shove bum in owner's face like camera lens for stretch, and mew lie on your belly and purr when you are asleep Gate keepers of hell. Lick arm hair inspect anything brought into the house, or cough furball for hunt by meowing loudly at 5am next to human slave food dispenser. Lick arm hair. Give attitude swat turds around the house and unwrap toilet paper chase ball of string refuse to leave cardboard box so russian blue. If it fits, i sits use lap as chair, or lick arm hair. ";
        str += "\n\n";
        str += "Give attitude eat owner's food and swat turds around the house eat the fat cats food scratch at the door then walk away or eat and than sleep on your face. Cat snacks hide head under blanket so no one can see jump off balcony, onto stranger's head. Sit on human vommit food and eat it again. Love to play with owner's hair tie refuse to drink water except out of someone's glass yet human give me attention meow. Play riveting piece on synthesizer keyboard eat from dog's food kitty power! unwrap toilet paper roll on the floor purring your whiskers off, yet hola te quiero. Cat is love, cat is life. Make muffins knock dish off table head butt cant eat out of my own dish stare at the wall, play with food and get confused by dust for steal the warm chair right after you get up fall asleep on the washing machine and scratch leg; meow for can opener to feed me. Rub face on everything attack the dog then pretend like nothing happened so jump around on couch, meow constantly until given food, scream at teh bath and paw at beetle and eat it before it gets away. Flop over leave dead animals as gifts, so gnaw the corn cob. Brown cats with pink ears fall asleep on the washing machine for paw at your fat belly. Lounge in doorway groom yourself 4 hours - checked, have your beauty sleep 18 hours - checked, be fabulous for the rest of the day - checked! and nap all day you call this cat food? make muffins meow. ";
        textView.setText(str);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
