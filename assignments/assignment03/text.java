public List<PImage> getImages(){
        return this.images;
        }
public Point getPosition(){
        return this.position;
        }
public void setPosition(Point p){
        this.position = p;
        }
public int getImageIndex(){
        return this.imageIndex;
        }
public String getId() {
        return id;
        }
public int getActionPeriod() {
        return actionPeriod;
        }
public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
        }
public int getResourceCount() {
        return resourceCount;
        }
public int getResourceLimit() {
        return resourceLimit;
        }
public void nextImage()
        {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
        }
public int getAnimationPeriod()
        {
        if (MinerFull.class.equals(this.getClass()) || MinerNotFull.class.equals(this.getClass()) || OreBlob.class.equals(this.getClass()) || Quake.class.equals(this.getClass())) {
        return this.animationPeriod;
        }
        throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s", this.getClass()));
        }

public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);

public static boolean processLine(String line, WorldModel world, ImageStore imageStore)
        {
        String[] properties = line.split("\\s");
        if (properties.length > 0)
        {
        switch (properties[PROPERTY_KEY])
        {
        case BGND_KEY:
        return Background.parseBackground(properties, world, imageStore);
        case MINER_KEY:
        return MinerNotFull.parseMiner(properties, world, imageStore);
        case OBSTACLE_KEY:
        return Obstacle.parseObstacle(properties, world, imageStore);
        case ORE_KEY:
        return Ore.parseOre(properties, world, imageStore);
        case SMITH_KEY:
        return Blacksmith.parseSmith(properties, world, imageStore);
        case VEIN_KEY:
        return Vein.parseVein(properties, world, imageStore);
        }
        }

        return false;
        }ring