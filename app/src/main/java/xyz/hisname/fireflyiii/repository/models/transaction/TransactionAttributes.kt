package xyz.hisname.fireflyiii.repository.models.transaction

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

@Entity
data class TransactionAttributes(
        val updated_at: String,
        val created_at: String,
        val description: String,
        val transaction_description: String?,
        val date: LocalDateTime,
        @SerializedName("type")
        val transactionType: String,
        val identifier: Int,
        val journal_id: Long,
        val journal_description: String,
        val reconciled: Boolean,
        val amount: Double,
        val currency_id: Int,
        val currency_code: String,
        val currency_symbol: String,
        val currency_dp: Int,
        val foreign_amount: String?,
        val foreign_currency_id: Int,
        val foreign_currency_code: String?,
        val foreign_currency_symbol: String?,
        val foreign_currency_dp: String?,
        val bill_id: Int,
        val bill_name: String?,
        val category_id: Int,
        val category_name: String?,
        val budget_id: Int,
        val budget_name: String?,
        val notes: String?,
        val source_id: Int,
        val source_name: String,
        val source_iban: String?,
        val source_type: String?,
        val destination_id: Int,
        val destination_name: String?,
        val destination_iban: String?,
        val destination_type: String?,
        val sepa_ct_op: String?,
        val sepa_ct_id: String?,
        val sepa_country: String?,
        val sepa_ep: String?,
        val sepa_ci: String?,
        val sepa_batch_id: String?,
        val tags: String?,
        val internal_reference: String?,
        val bunq_payment_id: String?,
        val recurrence_id: Int?,
        val piggy_bank_name: String?
)