import gym
# env = gym.make('CartPole-v0')
env = gym.make('MountainCar-v0')
env.reset()
for _ in range(1000):
    env.render()  # renders one frame of the environment
    env.step(env.action_space.sample())  # take a random action
env.close()
