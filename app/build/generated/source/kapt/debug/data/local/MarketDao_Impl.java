package data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MarketDao_Impl implements MarketDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MarketEntity> __insertionAdapterOfMarketEntity;

  public MarketDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMarketEntity = new EntityInsertionAdapter<MarketEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `market_items` (`symbol`,`price`,`timestamp`,`changePrice`,`changePercent`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MarketEntity entity) {
        if (entity.getSymbol() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getSymbol());
        }
        statement.bindDouble(2, entity.getPrice());
        statement.bindLong(3, entity.getTimestamp());
        statement.bindDouble(4, entity.getChangePrice());
        statement.bindDouble(5, entity.getChangePercent());
      }
    };
  }

  @Override
  public Object insertMarketItems(final List<MarketEntity> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMarketEntity.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<MarketEntity>> getAllMarketItems() {
    final String _sql = "SELECT * FROM market_items WHERE symbol != 'UNKNOWN' ORDER BY symbol ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"market_items"}, new Callable<List<MarketEntity>>() {
      @Override
      @NonNull
      public List<MarketEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "symbol");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfChangePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "changePrice");
          final int _cursorIndexOfChangePercent = CursorUtil.getColumnIndexOrThrow(_cursor, "changePercent");
          final List<MarketEntity> _result = new ArrayList<MarketEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MarketEntity _item;
            final String _tmpSymbol;
            if (_cursor.isNull(_cursorIndexOfSymbol)) {
              _tmpSymbol = null;
            } else {
              _tmpSymbol = _cursor.getString(_cursorIndexOfSymbol);
            }
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final double _tmpChangePrice;
            _tmpChangePrice = _cursor.getDouble(_cursorIndexOfChangePrice);
            final double _tmpChangePercent;
            _tmpChangePercent = _cursor.getDouble(_cursorIndexOfChangePercent);
            _item = new MarketEntity(_tmpSymbol,_tmpPrice,_tmpTimestamp,_tmpChangePrice,_tmpChangePercent);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getMarketItemBySymbol(final String symbol,
      final Continuation<? super MarketEntity> $completion) {
    final String _sql = "SELECT * FROM market_items WHERE symbol = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (symbol == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, symbol);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MarketEntity>() {
      @Override
      @Nullable
      public MarketEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "symbol");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfChangePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "changePrice");
          final int _cursorIndexOfChangePercent = CursorUtil.getColumnIndexOrThrow(_cursor, "changePercent");
          final MarketEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpSymbol;
            if (_cursor.isNull(_cursorIndexOfSymbol)) {
              _tmpSymbol = null;
            } else {
              _tmpSymbol = _cursor.getString(_cursorIndexOfSymbol);
            }
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final double _tmpChangePrice;
            _tmpChangePrice = _cursor.getDouble(_cursorIndexOfChangePrice);
            final double _tmpChangePercent;
            _tmpChangePercent = _cursor.getDouble(_cursorIndexOfChangePercent);
            _result = new MarketEntity(_tmpSymbol,_tmpPrice,_tmpTimestamp,_tmpChangePrice,_tmpChangePercent);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
