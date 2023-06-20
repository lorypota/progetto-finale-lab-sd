package it.unimib.finalproject.database.command.hash;

import java.util.AbstractMap;
import java.util.stream.Collectors;

import it.unimib.finalproject.database.command.Command;
import it.unimib.finalproject.database.resp.types.RESPArray;
import it.unimib.finalproject.database.resp.types.RESPBulkString;
import it.unimib.finalproject.database.resp.types.RESPError;
import it.unimib.finalproject.database.resp.types.RESPNumber;
import it.unimib.finalproject.database.resp.types.RESPString;
import it.unimib.finalproject.database.resp.types.RESPType;

public class HValsCommand extends Command {

    @Override
    public String getCommandName() {
        return "HVALS";
    }

    @Override
    public String getCommandSyntax() {
        return "<key:string>";
    }

    @Override
    public RESPType execute(AbstractMap<String, Object> store, RESPType[] args) throws RESPError {
        if (!(args.length == 1 &&
                (args[0] instanceof RESPString))) {
            throw this.getSyntaxError();
        }

        var key = ((RESPString) args[0]).getString();

        // Returns 0 if not a hash, or hash non-existent
        var hash = store.get(key);
        if (!(hash instanceof AbstractMap<?, ?>)) {
            return new RESPArray();
        }

        var hashValue = (AbstractMap<?, ?>) hash;

        return hashValue.entrySet()
                .stream()
                .map(entry -> entry.getValue() instanceof String ? new RESPBulkString(entry.getValue().toString())
                        : new RESPNumber((Integer) entry.getValue()))
                .collect(Collectors.toCollection(RESPArray::new));
    }

}
