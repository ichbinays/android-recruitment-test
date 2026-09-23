package data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\u0016J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\bH\u0016J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\bH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0016J\b\u0010\u0010\u001a\u00020\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Ldata/repository/MarketRepositoryImpl;", "Ldata/repository/MarketRepository;", "socketService", "Ldata/remote/MarketSocketService;", "marketDao", "Ldata/local/MarketDao;", "(Ldata/remote/MarketSocketService;Ldata/local/MarketDao;)V", "getLocalMarketItems", "Lkotlinx/coroutines/flow/Flow;", "", "Ldata/local/MarketEntity;", "getSocketConnectionStatus", "", "observeAndSaveRemoteMarketData", "", "startMarketUpdates", "stopMarketUpdates", "app_debug"})
public final class MarketRepositoryImpl implements data.repository.MarketRepository {
    @org.jetbrains.annotations.NotNull()
    private final data.remote.MarketSocketService socketService = null;
    @org.jetbrains.annotations.NotNull()
    private final data.local.MarketDao marketDao = null;
    
    @javax.inject.Inject()
    public MarketRepositoryImpl(@org.jetbrains.annotations.NotNull()
    data.remote.MarketSocketService socketService, @org.jetbrains.annotations.NotNull()
    data.local.MarketDao marketDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<data.local.MarketEntity>> getLocalMarketItems() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> getSocketConnectionStatus() {
        return null;
    }
    
    @java.lang.Override()
    public void startMarketUpdates() {
    }
    
    @java.lang.Override()
    public void stopMarketUpdates() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<kotlin.Unit> observeAndSaveRemoteMarketData() {
        return null;
    }
}