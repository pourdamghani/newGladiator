class Player{
    private Integer alive_power,alive_troops;
    private Integer id;

    Player(Integer id, Integer alive_troops, Integer alive_power){
        this.id = id;
        this.alive_troops = alive_troops;
        this.alive_power = alive_power;
    }

    public Integer getAlive_power() { return alive_power; }

    public Integer getId() {
        return id;
    }

    public void decrease(Integer amount){
        alive_power = alive_power-amount;
        alive_troops = alive_troops-1;
    }
    public void increase(Integer amount){
        alive_power = alive_power+amount;
        alive_troops = alive_troops+1;

    }

    public boolean empty(){ return (alive_power == 0 || alive_troops == 0); }
    public boolean isFirstPlayer(){ return id == 1; }
}
