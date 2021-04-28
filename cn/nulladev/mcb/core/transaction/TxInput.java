package cn.nulladev.mcb.core.transaction;

import cn.nulladev.mcb.core.BlockChain;
import cn.nulladev.mcb.core.User;
import cn.nulladev.mcb.sample.SampleUsers;
import cn.nulladev.mcb.utils.CommonHelper;
import cn.nulladev.mcb.utils.TypeTrans;

public class TxInput {
	
	private String _tx_hash;
	private int _num;
	private String _sign;
	
	private TxInput() {}
	
	public static TxInput create(String hash, int num) {
		TxInput input = new TxInput();
		input._tx_hash = hash;
		input._num = num;
		return input;
	}
	
	public String getHash() {
		return this._tx_hash;
	}
	
	public int getIndex() {
		return this._num;
	}
	
	public String getSign() {
		return this._sign;
	}
	
	public TxInput genSign(User user) throws Exception {
		this._sign = user.sign(_tx_hash);
		return this;
	}
	
	public TxInput genRandomSign() throws Exception {
		this._sign = SampleUsers.sampleUser2.sign(CommonHelper.getRandomString(32));
		return this;
	}
	
	public TxOutput getUTXO(BlockChain chain) {
		return chain.pool.getListClone().stream().filter(t->t.matchInput(this)).findAny().orElse(null);
	}
	
	public byte[] getRaw() {
		byte[] b1 = _tx_hash.getBytes();
		byte[] b2 = TypeTrans.int2Byte(_num);
		byte[] b3 = _sign.getBytes();
		return CommonHelper.mergeByteArrays(b1, b2, b3);
	}

}
