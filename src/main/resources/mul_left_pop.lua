local listKey = ARGV[1];
local limit = ARGV[2];

local range = redis.call('lRange', listKey, 0, limit - 1);
redis.call('lTrim', listKey, limit, -1);
return range;