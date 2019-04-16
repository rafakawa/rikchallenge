package br.com.rik.rikchallenge.order

import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.time.LocalDateTime

interface OrderDAO {

    @SqlUpdate("""
        INSERT INTO payment_order (
            id,
            client_id,
            buyer_name,
            buyer_email,
            buyer_cpf,
            payment_amount,
            payment_type,
            card_holder_name,
            card_number,
            card_expiration_date,
            card_cvv,
            order_date
        ) VALUES (
            :id,
            :clientId,
            :buyerName,
            :buyerEmail,
            :buyerCpf,
            :paymentAmount,
            :paymentType,
            :cardHolderName,
            :cardNumber,
            :cardExpirationDate,
            :cardCvv,
            :orderDate
        );
    """)
    fun saveOrder(
        id: String,
        clientId: String,
        buyerName: String,
        buyerEmail: String,
        buyerCpf: String,
        paymentAmount: Double,
        paymentType: String,
        cardHolderName: String?,
        cardNumber: String?,
        cardExpirationDate: String?,
        cardCvv: String?,
        orderDate: LocalDateTime
    )

    @SqlQuery("""
        SELECT
            id                        AS id,
            client_id                 AS clientId,
            buyer_name                AS buyerName,
            buyer_email               AS buyerEmail,
            buyer_cpf                 AS buyerCpf,
            payment_amount            AS paymentAmount,
            payment_type              AS paymentType,
            card_holder_name          AS cardHolderName,
            card_number               AS cardNumber,
            card_expiration_date      AS cardExpirationDate,
            card_cvv                  AS cardCvv,
            order_date                AS orderDate
        FROM payment_order po
        WHERE po.id = :id;
    """)
    fun findOrderById(id: String) : PaymentOrderDataModel?

    @SqlUpdate("""
        INSERT INTO payment_order_status (
            id,
            order_id,
            order_status,
            payload_type,
            payload,
            status_date
        ) VALUES (
            :id,
            :orderId,
            :orderStatus,
            :payloadType,
            :payload,
            :statusDate
        );
    """)
    fun saveOrderStatus(
        id: String,
        orderId: String,
        orderStatus: String,
        payloadType: String,
        payload: String,
        statusDate: LocalDateTime
    )

    @SqlQuery("""
        SELECT
            id                       AS id,
            order_id                 AS orderId,
            order_status             AS orderStatus,
            payload_type             AS payloadType,
            payload                  AS payload,
            status_date              AS statusDate
        FROM payment_order_status os
        WHERE os.id = :id;
    """)
    fun findOrderStatusById(id: String) : PaymentStatusDataModel?
}