package di;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = MarketApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface MarketApplication_GeneratedInjector {
  void injectMarketApplication(MarketApplication marketApplication);
}
