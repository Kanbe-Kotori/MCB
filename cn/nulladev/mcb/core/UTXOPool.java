package cn.nulladev.mcb.core;

import java.util.ArrayList;

import cn.nulladev.mcb.core.transaction.TxOutput;

public class UTXOPool {
	
	protected final ArrayList<TxOutput> _list;
	
	public UTXOPool() {
		this._list = new ArrayList<TxOutput>();
	}
	
	public boolean contains(TxOutput output) {
		return _list.contains(output);
	}
	
	public boolean add(ArrayList<TxOutput> arrayList) {
		return _list.addAll(arrayList);
	}
	
	public boolean add(TxOutput arrayList) {
		return _list.add(arrayList);
	}
	
	public boolean remove(TxOutput output) {
		return _list.remove(output);
	}
	
	public ArrayList<TxOutput> getListClone() {
		return (ArrayList<TxOutput>) this._list.clone();
	}

}
