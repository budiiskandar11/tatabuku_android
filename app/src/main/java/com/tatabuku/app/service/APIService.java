package com.tatabuku.app.service;

import com.tatabuku.app.model.DefaultPostModel;
import com.tatabuku.app.model.fixed_asset.DashboardAssetRequest;
import com.tatabuku.app.model.fixed_asset.DashboardAssetResponse;
import com.tatabuku.app.model.fixed_asset.DetailAssetRequest;
import com.tatabuku.app.model.fixed_asset.DetailAssetResponse;
import com.tatabuku.app.model.fixed_asset.GetCustomerListRequest;
import com.tatabuku.app.model.fixed_asset.GetCustomerListResponse;
import com.tatabuku.app.model.fixed_asset.GetPurchasesRequest;
import com.tatabuku.app.model.fixed_asset.GetPurchasesResponse;
import com.tatabuku.app.model.fixed_asset.GetSupplierRequest;
import com.tatabuku.app.model.fixed_asset.GetSupplierResponse;
import com.tatabuku.app.model.fixed_asset.JualAssetRequest;
import com.tatabuku.app.model.fixed_asset.JualAssetResponse;
import com.tatabuku.app.model.fixed_asset.ListAssetRequest;
import com.tatabuku.app.model.fixed_asset.ListAssetResponse;
import com.tatabuku.app.model.fixed_asset.TambahAssetRequest;
import com.tatabuku.app.model.fixed_asset.TambahAssetResponse;
import com.tatabuku.app.model.home.HomepageDataResponse;
import com.tatabuku.app.model.home.HomepageModel;
import com.tatabuku.app.model.pembelian.AddDPModel;
import com.tatabuku.app.model.pembelian.AddProdukModel;
import com.tatabuku.app.model.pembelian.AddSupplierModel;
import com.tatabuku.app.model.pembelian.CancelOrderResponse;
import com.tatabuku.app.model.pembelian.CategoryListResponse;
import com.tatabuku.app.model.pembelian.CityDataResponse;
import com.tatabuku.app.model.pembelian.CreateOrderModel;
import com.tatabuku.app.model.pembelian.DashboardSupplierResponse;
import com.tatabuku.app.model.pembelian.DefaultResponse;
import com.tatabuku.app.model.pembelian.DownPaymentListResponse;
import com.tatabuku.app.model.pembelian.EditProdukModel;
import com.tatabuku.app.model.pembelian.GetReceivedModel;
import com.tatabuku.app.model.pembelian.GetWarehouseResponse;
import com.tatabuku.app.model.pembelian.InvoiceListResponse;
import com.tatabuku.app.model.pembelian.LoadPaymentModel;
import com.tatabuku.app.model.pembelian.LoginModel;
import com.tatabuku.app.model.pembelian.LoginResponse;
import com.tatabuku.app.model.pembelian.LunasDataResponse;
import com.tatabuku.app.model.pembelian.OrderDetailResponse;
import com.tatabuku.app.model.pembelian.OrderListResponse;
import com.tatabuku.app.model.pembelian.PaymentDataModel;
import com.tatabuku.app.model.pembelian.PaymentDataResponse;
import com.tatabuku.app.model.pembelian.PaymentDetailDataResponse;
import com.tatabuku.app.model.pembelian.PaymentEditResponse;
import com.tatabuku.app.model.pembelian.PaymentIdModel;
import com.tatabuku.app.model.pembelian.PaymentTermResponse;
import com.tatabuku.app.model.pembelian.PembayaranResponse;
import com.tatabuku.app.model.pembelian.PenerimaanBarangResponse;
import com.tatabuku.app.model.pembelian.PrivacyModel;
import com.tatabuku.app.model.pembelian.PrivacyPolicyRespone;
import com.tatabuku.app.model.pembelian.ProdukListResponse;
import com.tatabuku.app.model.pembelian.ProvinceDataResponse;
import com.tatabuku.app.model.pembelian.RegisterModel;
import com.tatabuku.app.model.pembelian.RegisterResponse;
import com.tatabuku.app.model.pembelian.RekeningDataModel;
import com.tatabuku.app.model.pembelian.RekeningDataResponse;
import com.tatabuku.app.model.pembelian.RepostPaymentModel;
import com.tatabuku.app.model.pembelian.ReturnOrderModel;
import com.tatabuku.app.model.pembelian.SaveOrderResponse;
import com.tatabuku.app.model.pembelian.SubdistrictDataResponse;
import com.tatabuku.app.model.pembelian.SubmitOrderModel;
import com.tatabuku.app.model.pembelian.SubmitOrderResponse;
import com.tatabuku.app.model.pembelian.SubmitPaymentModel;
import com.tatabuku.app.model.pembelian.SubmitReceivedModel;
import com.tatabuku.app.model.pembelian.SupplierListResponse;
import com.tatabuku.app.model.pembelian.UpdateOrderModel;
import com.tatabuku.app.model.penjualan.AddCustomerModel;
import com.tatabuku.app.model.penjualan.CreateCustomerResponse;
import com.tatabuku.app.model.penjualan.CustomerDPResponse;
import com.tatabuku.app.model.penjualan.CustomerDetailDPResponse;
import com.tatabuku.app.model.penjualan.CustomerDetailHutangResponse;
import com.tatabuku.app.model.penjualan.CustomerDetailPenjualanResponse;
import com.tatabuku.app.model.penjualan.CustomerDetailPostModel;
import com.tatabuku.app.model.penjualan.DPPostModel;
import com.tatabuku.app.model.penjualan.DashboardCustomerResponse;
import com.tatabuku.app.model.penjualan.DashboardCustomerTotalResponse;
import com.tatabuku.app.model.penjualan.DashboardHutangCustomerResponse;
import com.tatabuku.app.model.penjualan.DashboardTotalPenjualanResponse;
import com.tatabuku.app.model.penjualan.EditCustomerModel;
import com.tatabuku.app.model.penjualan.FilterPostModel;
import com.tatabuku.app.model.penjualan.IdPostModel;
import com.tatabuku.app.model.penjualan.LoadCustomerResponse;
import com.tatabuku.app.model.pembelian.LoadProductResponse;
import com.tatabuku.app.model.penjualan.OrderDetailPenjualanResponse;
import com.tatabuku.app.model.penjualan.PartnerIdPostModel;
import com.tatabuku.app.model.penjualan.PengirimanBarangResponse;
import com.tatabuku.app.model.pembelian.ProductIdPostModel;
import com.tatabuku.app.model.penjualan.SaleIdPostModel;
import com.tatabuku.app.model.penjualan.ShippingReceivedModel;
import com.tatabuku.app.model.rekening.CatatBiayaRequest;
import com.tatabuku.app.model.rekening.CatatBiayaResponse;
import com.tatabuku.app.model.rekening.CreateBankAccountRequest;
import com.tatabuku.app.model.rekening.CreateBankAccountResponse;
import com.tatabuku.app.model.rekening.DashboardRekeningRequest;
import com.tatabuku.app.model.rekening.DashboardRekeningResponse;
import com.tatabuku.app.model.rekening.DeleteBiayaRequest;
import com.tatabuku.app.model.rekening.DeleteBiayaResponse;
import com.tatabuku.app.model.rekening.PindahBukuRequest;
import com.tatabuku.app.model.rekening.PindahBukuResponse;
import com.tatabuku.app.model.rekening.PostedBiayaRequest;
import com.tatabuku.app.model.rekening.PostedBiayaResponse;
import com.tatabuku.app.model.rekening.RekeningDetailRequest;
import com.tatabuku.app.model.rekening.RekeningDetailResponse;
import com.tatabuku.app.model.rekening.SubmitCatatBiayaRequest;
import com.tatabuku.app.model.rekening.SubmitCatatBiayaResponse;
import com.tatabuku.app.model.rekening.SubmitEditBiayaRequest;
import com.tatabuku.app.model.rekening.SubmitEditBiayaResponse;
import com.tatabuku.app.model.rekening.SubmitPindahBukuRequest;
import com.tatabuku.app.model.rekening.SubmitPindahBukuResponse;
import com.tatabuku.app.model.titip_jurnal.CreateTitipJurnalRequest;
import com.tatabuku.app.model.titip_jurnal.CreateTitipJurnalResponse;
import com.tatabuku.app.model.titip_jurnal.DetailTitipJurnalRequest;
import com.tatabuku.app.model.titip_jurnal.DetailTitipJurnalResponse;
import com.tatabuku.app.model.titip_jurnal.LoadTitipJurnalRequest;
import com.tatabuku.app.model.titip_jurnal.LoadTitipJurnalResponse;
import com.tatabuku.app.model.pembelian.PrivacyPolicyResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @POST("/web/session/authenticate")
    Call<LoginResponse> login(@Body LoginModel loginModel);

    @GET("/api/res.country.state?filter=[[\"country_id.code\",\"=\",\"ID\"]]&query={id, name,code}")
    Call<ProvinceDataResponse> getProvinceList();

    @GET("/api/res.country.city")
    Call<CityDataResponse> getCityList(@Query("query") String query, @Query("filter") String filter);

    @GET("/api/res.country.subdistrict")
    Call<SubdistrictDataResponse> getKecamatanList(@Query("query") String query, @Query("filter") String filter);

    // PEMBELIAN

    @GET("/api/partners.dashboard")
    Call<DashboardSupplierResponse> getDashboardSupplier(@Query("query") String query);

    @GET("/api/res.partner")
    Call<SupplierListResponse> getSupplierList(@Query("query") String query, @Query("filter") String filter, @Query("sort") String sort);

    @GET("/api/res.partner?filter=[[\"supplier\",\"=\",true],[\"partner_rating\",\"in\",[\"5\",\"4\"]]]")
    Call<SupplierListResponse> getFavoriteSupplierList(@Query("query") String query);

    @GET("/api/purchase.order")
    Call<OrderListResponse> getOrderList(@Query("query") String query, @Query("filter") String filter);

    @GET("/api/account.invoice")
    Call<InvoiceListResponse> getInvoiceList(@Query("query") String query, @Query("filter") String filter);

    @GET("/api/tatabuku.downpayment")
    Call<DownPaymentListResponse> getDPList(@Query("query") String query, @Query("filter") String filter);

    @GET("/api/account.invoice")
    Call<LunasDataResponse> getLunasList(@Query("query") String query, @Query("filter") String filter);

    @GET("/api/account.payment.term/?query={id, name}")
    Call<PaymentTermResponse> getPaymentTerm();

    @POST("/api/res.partner")
    Call<DefaultResponse> addSupplier(@Body AddSupplierModel addSupplierModel);

    @POST("/create_customer")
    Call<CreateCustomerResponse> addCustomer(@Body AddCustomerModel addCustomerModel);

    @POST("/api/product.template")
    Call<DefaultResponse> addProduk(@Body AddProdukModel addProdukModel);

    @GET("/api/product.template/?query={id,default_code,image,name,categ_id{id,name},standard_price, qty_available}")
    Call<ProdukListResponse> getProduk(@Query("filter") String filter);

    @GET("/api/product.category/?query={id,name}")
    Call<CategoryListResponse> getCategory();

    @GET("/api/res.company/?query={street, street2, city, zip, phone}")
    Call<GetWarehouseResponse> getWarehouseAddress();

    @POST("/create_purchase")
    Call<SaveOrderResponse> saveOrder(@Body CreateOrderModel createOrderModel);

    @POST("/add_purchase_line")
    Call<SaveOrderResponse> updateOrder(@Body UpdateOrderModel updateOrderModel);

    @POST("/checkout_purchase")
    Call<SubmitOrderResponse> submitOrder(@Body SubmitOrderModel submitOrderModel);

    @POST("/cancel_purchase")
    Call<CancelOrderResponse> cancelOrder(@Body IdPostModel idPostModel);

    @GET("/api/purchase.order")
    Call<OrderDetailResponse> getOrderDetail(@Query("query") String query, @Query("filter") String filter);

    @POST("/reload_picking")
    Call<PenerimaanBarangResponse> getStockPicking(@Body GetReceivedModel getReceivedModel);

    @POST("/receive_picking")
    Call<SubmitOrderResponse> submitReceived(@Body SubmitReceivedModel submitReceivedModel);

    @POST("/button_add_dp")
    Call<RekeningDataResponse> getRekeningData(@Body RekeningDataModel rekeningDataModel);

    @POST("/create_dp_purchase")
    Call<SubmitOrderResponse> addDP(@Body AddDPModel addDPModel);

    @POST("/refund_dp_purchase")
    Call<SubmitOrderResponse> refundDP(@Body AddDPModel addDPModel);

    @POST("/return_product_purchase")
    Call<SubmitOrderResponse> submitRetur(@Body ReturnOrderModel returnOrderModel);

    @POST("/load_open_invoice")
    Call<PaymentDataResponse> getPaymentData(@Body PaymentDataModel paymentDataModel);

    @POST("/load_paid_invoices")
    Call<PaymentDataResponse> getPaidPaymentData(@Body PaymentDataModel paymentDataModel);

    @POST("/pay_supplier_invoice")
    Call<SubmitOrderResponse> submitPaymentData(@Body SubmitPaymentModel submitPaymentModel);

    @POST("/cancel_payment_invoice")
    Call<SubmitOrderResponse> cancelPaymentData(@Body PaymentDataModel paymentDataModel);

    @POST("/load_payment")
    Call<PembayaranResponse> getPaymentList(@Body LoadPaymentModel loadPaymentModel);

    @POST("/open_posted_payment")
    Call<PaymentDetailDataResponse> getDetailPayment(@Body PaymentIdModel paymentIdModel);

    @POST("/cancel_payment")
    Call<PaymentEditResponse> cancelPayment(@Body PaymentIdModel paymentIdModel);

    @POST("/payment_reposted")
    Call<PaymentEditResponse> repostPaymentData(@Body RepostPaymentModel repostPaymentModel);

    @POST("/load_product")
    Call<LoadProductResponse> getProductData(@Body ProductIdPostModel productIdPostModel);

    @POST("/edit_product")
    Call<SubmitOrderResponse> updateProductData(@Body EditProdukModel editProdukModel);

    // PENJUALAN

    @POST("/get_penjualan_customer")
    Call<DashboardTotalPenjualanResponse> getDashboardTotalPenjualan(@Body DefaultPostModel defaultPostModel);

    @POST("/get_list_penjualan_customer")
    Call<DashboardCustomerResponse> getListPenjualanCustomer(@Body FilterPostModel filterPostModel);

    @POST("/get_list_payment_customer")
    Call<DashboardCustomerResponse> getListPaymentCustomer(@Body FilterPostModel filterPostModel);

    @POST("/get_total_customer_payment")
    Call<DashboardCustomerTotalResponse> getTotalPaymentCustomer(@Body DefaultPostModel defaultPostModel);

    @POST("/get_hutang_customer")
    Call<DashboardHutangCustomerResponse> getListHutangCustomer(@Body FilterPostModel filterPostModel);

    @POST("/get_total_hutang_customer")
    Call<DashboardCustomerTotalResponse> getTotalHutangCustomer(@Body DefaultPostModel defaultPostModel);

    @POST("/get_list_dp_customer")
    Call<DashboardCustomerResponse> getListDPCustomer(@Body FilterPostModel filterPostModel);

    @POST("/get_dashboard_customer_dp")
    Call<DashboardCustomerTotalResponse> getTotalDPCustomer(@Body DefaultPostModel defaultPostModel);

    @POST("/list_customer_barang")
    Call<CustomerDetailPenjualanResponse> getDetailPenjualanCustomer(@Body CustomerDetailPostModel customerDetailPostModel);

    @POST("/list_customer_hutang")
    Call<CustomerDetailHutangResponse> getDetailHutangCustomer(@Body CustomerDetailPostModel customerDetailPostModel);

    @POST("/list_customer_dp")
    Call<CustomerDetailDPResponse> getDetailDPCustomer(@Body CustomerDetailPostModel customerDetailPostModel);

    @POST("/create_dp_sale")
    Call<SubmitOrderResponse> addDPCustomer(@Body AddDPModel addDPModel);

    @POST("/refund_dp_sale")
    Call<SubmitOrderResponse> refundDPCustomer(@Body AddDPModel addDPModel);

    @POST("/load_posted_sale_dp")
    Call<CustomerDPResponse> getDataDPCustomer(@Body IdPostModel idPostModel);

    @POST("/posted_edit_customer_dp")
    Call<SubmitOrderResponse> updateDPCustomer(@Body DPPostModel dpPostModel);

    @POST("/create_sale")
    Call<SaveOrderResponse> saveOrderPenjualan(@Body CreateOrderModel createOrderModel);

    @POST("/cancel_sale")
    Call<CancelOrderResponse> cancelOrderPenjualan(@Body SaleIdPostModel saleIdPostModel);

    @POST("/add_sale_line")
    Call<SaveOrderResponse> updateOrderPenjualan(@Body UpdateOrderModel updateOrderModel);

    @POST("/checkout_sale")
    Call<SubmitOrderResponse> submitOrderPenjualan(@Body SubmitOrderModel submitOrderModel);

    @POST("/load_checkout_sale_form")
    Call<OrderDetailPenjualanResponse> getOrderDetailPenjualan(@Body SaleIdPostModel saleIdPostModel);

    @POST("/reload_sale_picking")
    Call<PengirimanBarangResponse> getPengirimanData(@Body SaleIdPostModel saleIdPostModel);

    @POST("/delivery_order")
    Call<SubmitOrderResponse> submitPengiriman(@Body ShippingReceivedModel shippingReceivedModel);

    @POST("/return_product_sale")
    Call<SubmitOrderResponse> submitReturPenjualan(@Body ReturnOrderModel returnOrderModel);

    @POST("/pay_customer_invoice")
    Call<SubmitOrderResponse> submitCustomerPaymentData(@Body SubmitPaymentModel submitPaymentModel);

    @POST("/cancel_customer_payment_invoice")
    Call<SubmitOrderResponse> cancelCustomerPaymentData(@Body PaymentDataModel paymentDataModel);

    @POST("/load_supplier_customer")
    Call<LoadCustomerResponse> getCustomerData(@Body PartnerIdPostModel partnerIdPostModel);

    @POST("/edit_supplier_customer")
    Call<SubmitOrderResponse> updateCustomerData(@Body EditCustomerModel editCustomerModel);


    // HOMEPAGE

    @POST("/home_page_data")
    Call<HomepageDataResponse> getHomepageData(@Body HomepageModel homepageModel);

    // ASSET

    @POST("/dashboard_asset")
    Call<DashboardAssetResponse> getDashboardFixedAsset(@Body DashboardAssetRequest dashboardAssetRequest);

    @POST("/asset_category_list")
    Call<ListAssetResponse> getAssetListByCategory(@Body ListAssetRequest listAssetRequest);

    @POST("/asset_detail")
    Call<DetailAssetResponse> getAssetDetail(@Body DetailAssetRequest detailAssetRequest);

    @POST("/get_supplier")
    Call<GetSupplierResponse> getSupplier(@Body GetSupplierRequest getSupplierRequest);

    @POST("/get_purchases")
    Call<GetPurchasesResponse> getPurchases(@Body GetPurchasesRequest getPurchasesRequest);

    @POST("/load_total_rekening")
    Call<DashboardRekeningResponse> getDashboardRekening(@Body DashboardRekeningRequest dashboardRekeningRequest);

    @POST("/load_rekening")
    Call<RekeningDetailResponse> getRekeningDetail(@Body RekeningDetailRequest rekeningDetailRequest);

    @POST("/load_pindah_buku")
    Call<PindahBukuResponse> getPindahBukuList(@Body PindahBukuRequest pindahBukuRequest);

    @POST("/load_biaya")
    Call<CatatBiayaResponse> getListBiaya(@Body CatatBiayaRequest catatBiayaRequest);

    @POST("/pindah_buku")
    Call<SubmitPindahBukuResponse> submitPindahBuku(@Body SubmitPindahBukuRequest submitPindahBukuRequest);

    @POST("/simpan_catatan_biaya")
    Call<SubmitCatatBiayaResponse> submitCatatBiaya(@Body SubmitCatatBiayaRequest submitCatatBiayaRequest);

    @POST("/load_posted_biaya")
    Call<PostedBiayaResponse> getPostedBiaya(@Body PostedBiayaRequest postedBiayaRequest);

    @POST("/post_edit_biaya")
    Call<SubmitEditBiayaResponse> editBiaya(@Body SubmitEditBiayaRequest submitEditBiayaRequest);

    @POST("/delete_edit_biaya")
    Call<DeleteBiayaResponse> deleteBiaya(@Body DeleteBiayaRequest deleteBiayaRequest);

    @POST("/create_asset")
    Call<TambahAssetResponse> createAsset(@Body TambahAssetRequest tambahAssetRequest);

    @POST("/list_customer")
    Call<GetCustomerListResponse> getCustomerList(@Body GetCustomerListRequest getCustomerListRequest);

    @POST("/sell_asset")
    Call<JualAssetResponse> sellAsset(@Body JualAssetRequest jualAssetRequest);

    @POST("/detail_titip_journal")
    Call<DetailTitipJurnalResponse> getDetailTitipJurnal(@Body DetailTitipJurnalRequest detailTitipJurnalRequest);

    @POST("/load_titip_journal")
    Call<LoadTitipJurnalResponse> getLoadTitipJurnal(@Body LoadTitipJurnalRequest loadTitipJurnalRequest);

    @POST("/create_titip_journal")
    Call<CreateTitipJurnalResponse> createTitipJurnal(@Body CreateTitipJurnalRequest createTitipJurnalRequest);

    @POST("/create_rekening")
    Call<CreateBankAccountResponse> createBankAccount(@Body CreateBankAccountRequest createBankAccountRequest);

    @POST("/signup_custom")
    Call<RegisterResponse> register(@Body RegisterModel registerModel);

    // terms condition
    @POST("/company_privacy_policy")
    Call<PrivacyPolicyRespone> getTermsCondition(@Body PrivacyModel privacyModel);
}
