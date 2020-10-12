package hozorghiyab.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hozorghiyab.R
import kotlinx.android.synthetic.main.mohasebe_tashvighi.*

class MohasebeTashvighi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mohasebe_tashvighi)
        btnMohasebe.setOnClickListener {
            var mizanHoghogh = Integer.parseInt(etMizanHoghogh.text.toString())
            var tedadMah = Integer.parseInt(etTedadMah.text.toString())
            //var mizanHoghoghGhanonKar = Integer.parseInt(etMizanHoghoghKarBeSaat.text.toString())
            //var saatKarkardRozaneh = Integer.parseInt(etSaatKarkardRozaneh.text.toString())
            //var tedadRozHayeMah = Integer.parseInt(etTedadRozHayeMah.text.toString())

            //var hoghoghSaatiGhanonKar = mizanHoghoghGhanonKar
            var a = (mizanHoghogh*tedadMah)/10000/7/30
            tcCalculate.setText(a.toString())

        }
    }
}