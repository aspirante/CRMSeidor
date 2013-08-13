package com.crystalis.menubar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.crystalis.listeners.ChangeFragmenListener;
import com.crystalis.view.R;

public class TabMenuFragment extends Fragment {
	// Variables
	private ChangeFragmenListener listener;
	private Button citas;
	private Button clientes;
	private Button productos;
	private Button ventas;
	private Button reportes;

	private FragmentActive currentTab;

	// Events
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tabmenu, container, false);
		initComponents(view);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			setCurrentTab((FragmentActive) savedInstanceState.getSerializable("selectedTab"));
		}
		this.setRetainInstance(true);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (getCurrentTab() != null) {
			toggleTabButtons(getCurrentTab());
		} else {
			currentTab = FragmentActive.TAB_CITAS;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable("selectedTab", getCurrentTab());
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (ChangeFragmenListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement MenuListener");
		}
	}

	@Override
	public void onStart() {
		super.onStart();

		citas.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				toggleTabButtons(FragmentActive.TAB_CITAS);
				listener.onChangeFragmentActionClick(FragmentActive.TAB_CITAS);
			}
		});

		clientes.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				toggleTabButtons(FragmentActive.TAB_CLIENTES);
				listener.onChangeFragmentActionClick(FragmentActive.TAB_CLIENTES);
			}
		});

		productos.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				toggleTabButtons(FragmentActive.TAB_PRODUCTOS);
				listener.onChangeFragmentActionClick(FragmentActive.TAB_PRODUCTOS);
			}
		});

		ventas.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				toggleTabButtons(FragmentActive.TAB_VENTAS);
				listener.onChangeFragmentActionClick(FragmentActive.TAB_VENTAS);
			}
		});

		reportes.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				toggleTabButtons(FragmentActive.TAB_REPORTES);
				listener.onChangeFragmentActionClick(FragmentActive.TAB_REPORTES);
			}
		});
	}

	// Methods
	private void initComponents(View view) {
		citas = (Button) view.findViewById(R.id.btn_citas);
		clientes = (Button) view.findViewById(R.id.btn_clientes);
		productos = (Button) view.findViewById(R.id.btn_productos);
		ventas = (Button) view.findViewById(R.id.btn_ventas);
		reportes = (Button) view.findViewById(R.id.btn_reportes);
	}

	public void toggleTabButtons(FragmentActive fragmentActive) {
		citas.setBackgroundResource(R.drawable.img_citas_titulo);
		clientes.setBackgroundResource(R.drawable.img_clientes_titulo);
		productos.setBackgroundResource(R.drawable.img_productos_titulo);
		ventas.setBackgroundResource(R.drawable.img_ventas_titulo);
		reportes.setBackgroundResource(R.drawable.img_reportes_titulo);

		setCurrentTab(fragmentActive);

		switch (fragmentActive) {
			case TAB_CITAS:
				citas.setBackgroundResource(R.drawable.img_citas_select);
				break;
			case TAB_CLIENTES:
				clientes.setBackgroundResource(R.drawable.img_clientes_select);
				break;
			case TAB_PRODUCTOS:
				productos.setBackgroundResource(R.drawable.img_productos_select);
				break;
			case TAB_VENTAS:
				ventas.setBackgroundResource(R.drawable.img_ventas_select);
				break;
			case TAB_REPORTES:
				reportes.setBackgroundResource(R.drawable.img_reportes_select);
				break;
			default:
				break;
		}
	}

	public FragmentActive getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(FragmentActive currentTab) {
		this.currentTab = currentTab;
	}
}
