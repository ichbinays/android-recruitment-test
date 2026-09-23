package data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H&J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H&J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003H&J\b\u0010\n\u001a\u00020\tH&J\b\u0010\u000b\u001a\u00020\tH&\u00a8\u0006\f"}, d2 = {"Ldata/repository/MarketRepository;", "", "getLocalMarketItems", "Lkotlinx/coroutines/flow/Flow;", "", "Ldata/local/MarketEntity;", "getSocketConnectionStatus", "", "observeAndSaveRemoteMarketData", "", "startMarketUpdates", "stopMarketUpdates", "app_debug"})
public abstract interface MarketRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<data.local.MarketEntity>> getLocalMarketItems();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Boolean> getSocketConnectionStatus();
    
    public abstract void startMarketUpdates();
    
    public abstract void stopMarketUpdates();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<kotlin.Unit> observeAndSaveRemoteMarketData();
}